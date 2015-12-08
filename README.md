# MyMusic App (Service)

mymusicapp-melvincabatuan created by Classroom for GitHub

This assignment illustrates the basic utilization of music Service in Android. It also introduces file access from the Assets directory.


## Problem:

Design and implement an Android app that utilizes Services to play music from files stored in the Assets directory.


## Accept

To accept the assignment, click the following URL:

https://classroom.github.com/assignment-invitations/684bd0c52f6c06914f8e4b674b1926b1

## Sample Solution:

https://github.com/DeLaSalleUniversity-Manila/mymusicapp-melvincabatuan

## Keypoints:

My MusicService.java
```Java
public class MusicService extends Service {

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";

    private MediaPlayer player; // media player to play the music



   // This method is called each time a request comes in from the app via an intent.
   // It processes the request by playing or stopping the song as appropriate.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();

        if (action.equals(ACTION_PLAY)) {
            String filename = intent.getStringExtra("filename"); // get filename from intent

            // stop any previous playing song
            if (player != null && player.isPlaying()) {
                player.stop();
                player.release();
            }

            try {
                AssetFileDescriptor afd;
                afd = getAssets().openFd(filename);  // open file from assets
                player = new MediaPlayer();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                player.prepare();
                player.setLooping(true);
                player.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // END ACTION_PLAY

        } else if (action.equals(ACTION_STOP)) {
            // stop any currently playing song
            if (player != null && player.isPlaying()) {
                player.stop();
               // player.release(); // causes IllegalStateException
            }
        }
        return START_NOT_STICKY;
    }



    // Stop service when onDestroy is called
    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Music Service Stopped", Toast.LENGTH_SHORT).show();
        player.stop();
        player.release();
    }




    // This method specifies how our service will deal with binding.
    // Binding is not supported here indicated by returning null.
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
```

In the MainActivity.java:
```Java
    private void setupPlaylist() {
        ListView lv = (ListView) findViewById(R.id.song_list);
        filenames = getResources().getStringArray(R.array.filenames);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.my_list_item, filenames);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra("filename", filenames[index]);
                intent.setAction(MusicService.ACTION_PLAY);
                startService(intent);
            }
        });
    }


    public void onClickStop(View view) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(MusicService.ACTION_STOP);
        startService(intent);
    }
```


## Submission Procedure with Git: 

```shell
$ cd /path/to/your/android/app/
$ git init
$ git add –all
$ git commit -m "your message, e.x. Assignment 1 submission"
$ git remote add origin <Assignment link copied from assignment github, e.x. https://github.com/DeLaSalleUniversity-Manila/secondactivityassignment-melvincabatuan.git>
$ git push -u origin master
<then Enter Username and Password>
```

Sample:

https://gist.github.com/melvincabatuan/1e861a7cb73220fb3337 

## Music credits:

1. Believe - "The Witcher" Theme: https://www.youtube.com/watch?v=Vr-2YmykHMI

2. Skyrim - Dovahkiin (Metal Cover): https://www.youtube.com/watch?v=UW7EnixZVNI 

3. Deathnote (Light's Theme) : https://www.youtube.com/watch?v=I_3pMKFl-Lw

## Screenshots:

![alt tag](https://github.com/DeLaSalleUniversity-Manila/mymusicapp-melvincabatuan/blob/master/device-2015-10-04-204259.png)

![alt tag](https://github.com/DeLaSalleUniversity-Manila/mymusicapp-melvincabatuan/blob/master/device-2015-10-04-204328.png)

"*Success is walking from failure to failure with no loss of enthusiasm.*" - Winston Churchill
