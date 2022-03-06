public class GameOver extends Music {
    private final String fileName = currentRelativePath.toAbsolutePath().toString() + "\\src\\sounds\\game_over.wav";

    @Override
    public void run() {
        playSound(fileName);
    }
}
