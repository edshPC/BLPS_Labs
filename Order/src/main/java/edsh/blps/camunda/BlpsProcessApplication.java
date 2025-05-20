package edsh.blps.camunda;

import edsh.blps.config.BeanProvider;
import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.PreUndeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.JakartaServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

@ProcessApplication
public class BlpsProcessApplication extends JakartaServletProcessApplication {

    @PostDeploy
    public void startProcess(ProcessEngine processEngine) {
        BeanProvider.getBean(BeanProvider.class).setProcessEngine(processEngine);
        System.out.println("Post Deploy " + processEngine);
    }

    @PreUndeploy
    public void remove() {
        System.out.println("Pre Undeploy");
    }

}
