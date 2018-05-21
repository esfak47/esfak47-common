package com.esfak47.common.job;

import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;

public interface Job extends Serializable, Runnable {

    String getId();

    String getName();

    /**
     * 获得进度
     *
     * @return 进度
     */
    int getProcess();

    void setProcess(int process);

    /**
     * 是否结束
     *
     * @return 是否结束
     */
    boolean isFinish();

    void setFinish(boolean finish);

    /**
     * 是否异已被取消
     *
     * @return 是否取消
     */
    boolean isCancelled();

    /**
     * 是否异已被取消
     *
     * @return 是否取消
     */

    void setCancelled(boolean cancelled);

    /**
     * 是否异常退出
     *
     * @return 是否异常退出
     */
    boolean isExitUnexpectedly();

    void setExitUnexpectedly(boolean exitUnexpectedly);

    Date getCreateTime();

    Date getFinishTime();

    void setFinishTime();

    boolean isAsync();




}
