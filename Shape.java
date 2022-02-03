public abstract class Shape{
    String color = "$PUT_YOUR_CHOICE_OF_COLOR"
    private int y;
    private int x;

    public Shape(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}