package com.fms.core.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ganesan on 31/05/16.
 */
public class Reader<Conf, T> {

    private Function<Conf, T> read;
    private Reader(Function<Conf, T> read) {
        this.read = read;
    }

    public static <A,B> Reader<A,B> of(Function<A,B> f) {
        return new Reader<>(f);
    }
    public static <A,B> Reader<A,B> pure(B b) {
        return new Reader<>((A a) -> b);
    }

    public <U> Reader<Conf, U> map(Function<T, U> mapper) {
        return of(this.read.andThen(mapper));
    }

    public <V> Reader<Conf,V> flatMap(Function<T,Reader<Conf,V>> toReader) {
        return of(conf -> toReader.apply(read.apply(conf)).read.apply(conf));
    }

    public <BiigerConf> Reader<BiigerConf, T> local(Function<BiigerConf, Conf> extradtFrom) {
        return of(extradtFrom.andThen(read));
    }

    public interface Datastore {
        List<String> runQuery(String string);
    }

    public interface EmailServer {
        void sendEmail(String to, String content);
    }

    public static Function<EmailServer, Runnable> emailInactive(Supplier<List<String>> inactive) {
        return emailServer -> () -> inactive.get().stream().forEach(s -> emailServer.sendEmail(s, "We miss you"));
    }

     public static Function<Datastore, Supplier<List<String>>> inactive() {
         return dataStore -> () -> dataStore.runQuery("select inactive");
    }

    public static Runnable retainUsers( Runnable emailInactive) {
       return () -> {
           System.out.println("emailing inactive users");
           emailInactive.run();
       };
    }

    public static Supplier<Function<Datastore, Supplier<List<String>>>> inactive = Reader::inactive;
    public static Function<Supplier<List<String>>, Function<EmailServer, Runnable>> emailInactive = Reader::emailInactive;

    public interface Config {
        EmailServer getEmailServer();
        Datastore getDataStore();
    }


    public static void main(String[] args) {

        Reader<Config, >

    }

}
