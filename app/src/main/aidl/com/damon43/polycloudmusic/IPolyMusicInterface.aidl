// IPolyMusicInterface.aidl
package com.damon43.polycloudmusic;

// Declare any non-default types here with import statements

interface IPolyMusicInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
            void openFile(String path);
            void open(in long [] list, int position, long sourceId, int sourceType);
            void playMusic();
            void stopMusic();
            void nextMusic();
            void backMusic();
            void enqueue(in long [] list, int action, long sourceId, int sourceType);
            void setRepeatMode(int repeatmode);
            void moveQueueItem(int from, int to);
            void playlistChanged();
            boolean isPlaying();
            long getDuration();
            long getPauseposition();
            long [] getQueue();
            String getSongName();
            String getAuthorName();
            String getMusicImg();
}
