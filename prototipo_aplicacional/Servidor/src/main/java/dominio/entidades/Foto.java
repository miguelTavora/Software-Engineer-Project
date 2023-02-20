package dominio.entidades;

public class Foto {

    // esta classe é so um exemplo
    // mas para ficar bem feito teria de utilizar imagens embebidas
    // que no caso não é muito relevante, visto que não existe interface grafica

    private String texto;

    public Foto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return "Foto{" +
                "texto='" + texto + '\'' +
                '}';
    }
}
