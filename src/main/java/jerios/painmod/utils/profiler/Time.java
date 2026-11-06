package jerios.painmod.utils.profiler;

import jerios.painmod.forge.PainShare;

// deadbones basic time profiler
public class Time {

    public static final Time INSTANCE = new Time();

    long start;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end(String name) {
        long elapsedTimeMillis = System.currentTimeMillis()-start;

        float elapsedTimeSec = elapsedTimeMillis/1000F;


        StringBuilder ms = new StringBuilder("Miliseconds Timer " + name );


        PainShare.LOG.info(ms);


    }


}
