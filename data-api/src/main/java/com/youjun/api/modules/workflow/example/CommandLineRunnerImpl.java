package com.youjun.api.modules.workflow.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youjun.api.modules.workflow.dto.Content;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2022/4/9
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) {
        /*loginInAs("system");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        logger.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            logger.info("\t > Process definition: " + pd);
        }*/

    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void processText() {

        loginInAs("system");

        Content content = pickRandomString();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

        logger.info("> Starting process to process content: " + content + " at " + formatter.format(new Date()));

        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("categorizeHumanProcess")
                .withName("Processing Content: " + content)
                .withVariable("content", content)
                .build());
        logger.info(">>> Created Process Instance: " + processInstance);


    }

    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void checkAndWorkOnTasksWhenAvailable() {
        loginInAs("salaboy");

        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        if (tasks.getTotalItems() > 0) {
            for (Task t : tasks.getContent()) {

                logger.info("> Claiming task: " + t.getId());
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(t.getId()).build());

                List<VariableInstance> variables = taskRuntime.variables(TaskPayloadBuilder.variables().withTaskId(t.getId()).build());
                VariableInstance variableInstance = variables.get(0);
                if (variableInstance.getName().equals("content")) {
                    Content contentToProcess = variableInstance.getValue();
                    logger.info("> Content received inside the task to approve: " + contentToProcess);

                    if (contentToProcess.getBody().contains("activiti")) {
                        logger.info("> User Approving content");
                        contentToProcess.setApproved(true);
                    } else {
                        logger.info("> User Discarding content");
                        contentToProcess.setApproved(false);
                    }
                    taskRuntime.complete(TaskPayloadBuilder.complete()
                            .withTaskId(t.getId()).withVariable("content", contentToProcess).build());
                }


            }

        } else {
            logger.info("> There are no task for me to work on.");
        }

    }


    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            Content contentToTag = (Content) integrationContext.getInBoundVariables().get("content");
            contentToTag.getTags().add(" :) ");
            integrationContext.addOutBoundVariable("content",
                    contentToTag);
            logger.info("Final Content: " + contentToTag);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            Content contentToDiscard = (Content) integrationContext.getInBoundVariables().get("content");
            contentToDiscard.getTags().add(" :( ");
            integrationContext.addOutBoundVariable("content",
                    contentToDiscard);
            logger.info("Final Content: " + contentToDiscard);
            return integrationContext;
        };
    }


    private Content pickRandomString() {
        String[] texts = {"hello from london", "Hi there from activiti!", "all good news over here.", "I've tweeted about activiti today.",
                "other boring projects.", "activiti cloud - Cloud Native Java BPM"};
        return new Content(texts[new Random().nextInt(texts.length)], false, null);
    }

    private void loginInAs(String username) {
        //UserDetails userDetails = new User(username, "123456", Collections.emptyList());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("login user:{}", username);

        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
    }
}
