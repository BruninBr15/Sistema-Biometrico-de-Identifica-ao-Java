package br.com.suauniversidade.biometria.Verificacao;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.Face;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

public class treinamento{
    public static void main(String[]){
        File diretorio= new File("src\\fotos");
        FilenameFilter filtroImagem= new FilenameFilter(){
            public boolean accept(File dir,String nome){
                return nome.endsWith(".jpg")|| nome.endWith(".gif")|| nome.endsWith(".png");
            }
        }
        File[] arquivos = diretorio.listFiles(filtroImagem);
        MatVector fotos= new MatVector(arquivos.length);
        Mat rotulos = new Mat(arquivos.length,1 ,cv_32sc1);
        IntBuffer rotulosBuffer = rotulos.createBuffer();
        int contador= 0;
        for(File imagem: arquivos) {
            Mat foto = imread(imagem.getAbsolutePath(),CV_LOAD_IMAGE_GRAYSCALE);
            int classe = Integer.parseInt(imagem.getName().split("\\.")[1]);
            System.out.println(classe);
            resize(foto,foto, new Size(160,160));
            fotos.put(contador, foto);
            rotuloBuffer.put(contador,classe);
            contador++;

        }
        FaceRecognizer eigenfaces= createEigenFaceRecognizer();
        FaceRecognizer fisherfaces = createFisherFaceRecognizer();
        FaceRecognizer lbph = createLBPHFaceRecognizer();

        eigenfaces.train(fotos, rotulos);
        eigenfaces.save("\\src\\recursos\\classificadorEigenFaces.yml");
        fisherfaces.train(fotos, rotulos);
        fisherfaces.save("\\src\\recursos\\classificadorFisherFaces.yml");
        lbph.train(fotos,rotulos);
        lbph.save("\\src\\recursos\\classificarLBPHFaces.yml")
       }
 }