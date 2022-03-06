public class Music extends Sound {
    private final String fileName = currentRelativePath.toAbsolutePath().toString() + "\\src\\sounds\\fat_rat.wav";

    @Override
    public void run() {
        playMusic(fileName);
    }
}
