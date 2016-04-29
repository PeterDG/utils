package com.peter.util.connection.request;

import java.util.List;

/**
 * Created by Pedro Gutierrez on 4/20/2016.
 */
public class RequestSender extends Thread {
    public Thread t;
    public List<Request> requests;
    public int executions;
    public int limit = 50;

    public RequestSender(List<Request> requests) {
        this.requests = requests;
        this.executions = requests.size();
    }

    @Override
    public void run() {
        if (this.executions > limit) {
            int middle = executions / 2;
            RequestSender parallelExecutorLower = new RequestSender(requests.subList(0, middle));
            RequestSender parallelExecutorUpper = new RequestSender(requests.subList(middle, executions));
            parallelExecutorLower.run();
            parallelExecutorUpper.run();
        } else {
            for (Request request : requests) {
                request.send();
            }
        }
    }

    @Override
    public void start() {
        if (t == null) {
            t = new Thread(this, getName());
            t.start();
        }
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
