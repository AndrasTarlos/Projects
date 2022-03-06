public class PickupApple extends Sound {
    private final String fileName = currentRelativePath.toAbsolutePath().toString() + "\\src\\sounds\\pickup_apple.wav";

    @Override
    public void run() {
        playSound(fileName);
    }
}
