package radiation;

public class Radiant {

    protected double Fe;

    protected String name;

    protected String spectrum;

    protected String directivity;

    /**
     * Конструктор
     * @param Fe
     * @param name
     */

    public Radiant(double Fe, String name) {
        this.Fe = Fe;
        this.name = name;
    }

    /**
     * Конструктор
     * @param Fe
     * @param name
     * @param spectrum
     */

    public Radiant(double Fe, String name, String spectrum) {
        this.Fe = Fe;
        this.name = name;
        this.spectrum = spectrum;
    }

    public Radiant(double Fe, String name, String spectrum, String directivity) {
        this.Fe = Fe;
        this.name = name;
        this.spectrum = spectrum;
        this.directivity = directivity;
    }

    public double getFe() {
        return Fe;
    }

    public void setFe(double Fe) {
        this.Fe = Fe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpectrum() {
        return spectrum;
    }

    public void setSpectrum(String spectrum) {
        this.spectrum = spectrum;
    }

    public String getDirectivity() {
        return directivity;
    }

    public void setDirectivity(String directivity) {
        this.directivity = directivity;
    }

    /**
     * Автоматическое преобразование в строку
     * @return
     */

    @Override
    public String toString() {
        return name;
    }
}
