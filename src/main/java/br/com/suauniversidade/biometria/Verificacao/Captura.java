package br.com.suauniversidade.biometria.Verificacao;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.Face;
import org.bytedeco.javacv.*;
import java.util.Scanner;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class Captura {
    public static void main(String[] args) {
        KeyEvent tecla = null;
        OpenCVFrameConverter.ToMat converteMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        String[] pessoas = {"", "pessoa1", "pessoa2"};
        
        try {
            camera.start();
            
            // Identifica a face na imagem ou video
            CascadeClassifier detectorFace = new CascadeClassifier("src\\recursos\\haarcascade_frontalface_alt.xml");
            FaceRecognizer reconhecedor = Face.createEigenFaceRecognizer();
            reconhecedor.load("src\\recursos\\classificadorEigenFaces.yml");
            
            CanvasFrame cFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());
            Frame frameCapturado = null;
            Mat imagemColorida = new Mat();
            int numeroAmostras = 10;
            int amostra = 1;
            
            System.out.println("Digite seu Id: ");
            Scanner cadastro = new Scanner(System.in);
            int idPessoa = cadastro.nextInt();
            
            while ((frameCapturado = camera.grab()) != null) {
                imagemColorida = converteMat.convert(frameCapturado);
                Mat imagemCinza = new Mat();
                cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
                
                RectVector facesDetectadas = new RectVector();
                detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, 
                    new Size(150, 150), new Size(500, 500));
                
                for (int i = 0; i < facesDetectadas.size(); i++) {
                    Rect dadosFace = facesDetectadas.get(i);
                    rectangle(imagemColorida, dadosFace, new Scalar(0, 0, 255, 0));
                    
                    Mat faceCapturada = new Mat(imagemCinza, dadosFace);
                    resize(faceCapturada, faceCapturada, new Size(160, 160));
                    
                    IntPointer rotulo = new IntPointer(1);
                    DoublePointer confianca = new DoublePointer(1);
                    reconhecedor.predict(faceCapturada, rotulo, confianca);
                    int predicao = rotulo.get(0);
                    
                    String nome;
                    if (predicao == -1) {
                        nome = "Desconhecido";
                    } else {
                        nome = pessoas[predicao] + "-" + confianca.get(0);
                    }
                    
                    int x = Math.max(dadosFace.tl().x() - 10, 0);
                    int y = Math.max(dadosFace.tl().y() - 10, 0);
                    putText(imagemColorida, nome, new Point(x, y), FONT_HERSHEY_PLAIN, 
                            1.4, new Scalar(0, 255, 0, 0));
                    
                    // Captura fotos quando tecla 'q' é pressionada
                    if (tecla != null && tecla.getKeyChar() == 'q') {
                        if (amostra <= numeroAmostras) {
                            imwrite("src\\fotos\\pessoa." + idPessoa + "." + amostra + ".jpg", faceCapturada);
                            System.out.println("foto " + amostra + " capturada\n");
                            amostra++;
                        }
                    }
                }
                
                if (cFrame.isVisible()) {
                    cFrame.showImage(converteMat.convert(imagemColorida));
                }
                
                if (amostra > numeroAmostras) {
                    break;
                }
            }
            
            cFrame.dispose();
            camera.stop();
            cadastro.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
