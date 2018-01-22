package online.himakeit.readhub.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author：LiXueLong
 * @date：2018/1/20
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class ThreadManager {
    public static final int LOOPER_TYPE_ANDROID_MAIN = 0;
    public static final int LOOPER_TYPE_THREAD_BG = 1;
    public static final int LOOPER_TYPE_THREAD_REQUEST = 2;
    public static final int LOOPER_TYPE_THREAD_TEMP = 3;

    private HandlerThread mHandlerThreadBg = null;
    private HandlerThread mHandlerThreadRequest = null;
    private HandlerThread mHandlerThreadTemp = null;

    /**
     * 后台线程、用户处理数据存储，业务逻辑等
     */
    private static final String THREAD_NAME_BG = "THREAD_BG";
    /**
     * 网络请求线程，用于分发网络请求，网络请求的处理利用临时线程池
     */
    private static final String THREAD_NAME_REQUEST = "THREAD_REQUEST";
    /**
     * 临时线程，用于通过handle处理一些临时逻辑、延迟逻辑
     */
    private static final String THREAD_NAME_TEMP = "THREAD_TEMP";
    private Handler mPostDelayTempHandler;

    /**
     * 临时线程池，用于通过Runnable处理其余临时逻辑
     */
    private ExecutorService mExecutorService;
    private static final int MAX_RUNNING_THREAD = 3;
    private static final String EXECUTOR_NAEM_TEMP = "EXCUTOR_TEMP";

    // TODO: 2018/1/20 不懂
    private static volatile ThreadManager mInstance;

    public static ThreadManager getmInstance() {
        if (mInstance == null) {
            synchronized (ThreadManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadManager();
                }
            }
        }
        return mInstance;
    }

    private ThreadManager() {
    }

    public Looper getLooper(int type) {
        if (type == LOOPER_TYPE_ANDROID_MAIN) {
            return Looper.getMainLooper();
        } else if (type == LOOPER_TYPE_THREAD_BG) {
            if (null == mHandlerThreadBg) {
                mHandlerThreadBg = new HandlerThread(THREAD_NAME_BG);
                mHandlerThreadBg.start();
            }
            return mHandlerThreadBg.getLooper();
        } else if (type == LOOPER_TYPE_THREAD_REQUEST) {
            if (null == mHandlerThreadRequest) {
                mHandlerThreadRequest = new HandlerThread(THREAD_NAME_REQUEST);
                mHandlerThreadRequest.start();
            }
            return mHandlerThreadRequest.getLooper();
        } else {
            if (null == mHandlerThreadTemp) {
                mHandlerThreadTemp = new HandlerThread(THREAD_NAME_TEMP);
                mHandlerThreadTemp.start();
            }
            return mHandlerThreadTemp.getLooper();
        }
    }

    public void start(Runnable runnable) {
        if (null == mExecutorService) {
            try {
                mExecutorService = Executors.newFixedThreadPool(MAX_RUNNING_THREAD, new CommonThreadFactory());
            } catch (Throwable throwable) {
                mExecutorService = Executors.newCachedThreadPool(new CommonThreadFactory());
            }
        }
        try {
            mExecutorService.submit(runnable);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void start(Runnable runnable, int seconds) {
        if (null == mPostDelayTempHandler) {
            mPostDelayTempHandler = new Handler(getLooper(LOOPER_TYPE_THREAD_TEMP));
        }
        mPostDelayTempHandler.postDelayed(runnable, seconds * 1000);
    }

    public void runOnUIThread(Runnable runnable) {
        try {
            new Handler(Looper.getMainLooper()).post(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CommonThreadFactory implements ThreadFactory {
        private final AtomicInteger poolNum = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNum = new AtomicInteger(1);
        private final String namePrefix;

        public CommonThreadFactory() {
            SecurityManager securityManager = System.getSecurityManager();
            group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = EXECUTOR_NAEM_TEMP + "-pool-" + poolNum.getAndIncrement();
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNum.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.MIN_PRIORITY) {
                thread.setPriority(Thread.MIN_PRIORITY);
            }
            return thread;
        }
    }

}
