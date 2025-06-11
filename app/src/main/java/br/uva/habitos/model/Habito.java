package br.uva.habitos.model;

import br.uva.habitos.R;

public enum Habito {

    CORRER("Correr", R.drawable.run),
    BEBER_AGUA("Beber Água", R.drawable.drink_water),
    ALONGAR("Alongar-se", R.drawable.alongamento),
    MEDITAR("Meditar", R.drawable.meditation),
    ESTUDAR("Estudar", R.drawable.study),
    LER("Ler um Livro", R.drawable.read),
    CAMINHAR("Caminhar", R.drawable.caminhar),
    RESPIRAR_FUNDO("Respirar", R.drawable.deep_breath);

    public static Habito acharPeloDisplayName(String displayName) {
        for(Habito habito : values())
            if(habito.displayName.equalsIgnoreCase(displayName))
                return habito;
        return null;
    }

    private final String displayName;
    private final int pngId;

    Habito(String displayName, int pngId) {
        this.displayName = displayName;
        this.pngId = pngId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPngId() {
        return pngId;
    }
}