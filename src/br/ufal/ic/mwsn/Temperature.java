package br.ufal.ic.mwsn;

/**
 *
 * @author williams
 */
public class Temperature {

    private float value;
    private Position position;

    public Temperature(float v, int x, int y) {
        this.value = v;
        this.position = new Position(x, y);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
