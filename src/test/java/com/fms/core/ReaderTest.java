package com.fms.core;

import com.fms.core.common.Promise;
import com.fms.core.common.React;
import com.fms.core.common.Reader;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ganesan on 02/06/16.
 */
public class ReaderTest {

    public interface Datastore {
        List<String> runQuery(String string);
    }

    public interface EmailServer {
        void sendEmail(String to, String content);
    }

    public static Reader<EmailServer, Runnable> emailInactive(Supplier<List<String>> inactive) {
        return Reader.of(emailServer -> () -> inactive.get().stream().forEach(s -> emailServer.sendEmail(s, "We miss you")));
    }

    public static Reader<Datastore, Supplier<List<String>>> inactive() {
        return Reader.of(dataStore -> () -> dataStore.runQuery("select inactive"));
    }

    public static void retainUsers(Runnable emailInactive) {
        System.out.println("emailing inactive users");
        emailInactive.run();
    }

    public static Supplier<Reader<Datastore, Supplier<List<String>>>> inactive = ReaderTest::inactive;
    public static Function<Supplier<List<String>>, Reader<EmailServer, Runnable>> emailInactive = ReaderTest::emailInactive;
    public static Consumer<Runnable> retainUsers = ReaderTest::retainUsers;

    public interface Config {
        EmailServer getEmailServer();
        Datastore getDataStore();
    }


    public static Reader<Config, Promise<String>> executeBusiness() {
        return Reader.of(config ->  React.of(() ->inactive.get().with(config.getDataStore()))
                .then(mailIds -> emailInactive.apply(mailIds).with(config.getEmailServer()))
                .thenV(retainUsers)
                .then(r -> "success").getPromise()
        );
    }


    public static void main(String[] args) throws Exception {
        System.out.println(executeBusiness().with(new Config() {
            @Override
            public EmailServer getEmailServer() {
                return (c, to) -> System.out.println("sending email to "+ to +" content :"+ c);
            }

            @Override
            public Datastore getDataStore() {
                return q -> Arrays.asList("imdganesan@gmail.com");
            }
        }).get().get());



    }
}
