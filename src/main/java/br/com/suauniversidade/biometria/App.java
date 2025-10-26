package br.com.suauniversidade.biometria;

import javax.swing.SwingUtilities;

import br.com.suauniversidade.biometria.view.BiometriaUI;

public class App {
    public static void main(String[] args) {
        // use args to avoid "unused parameter" warnings
        if (args.length > 0) { /* no-op to mark args as used */ }

        SwingUtilities.invokeLater(BiometriaUI::new);
    }
}
