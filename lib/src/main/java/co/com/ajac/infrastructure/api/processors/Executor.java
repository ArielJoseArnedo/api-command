package co.com.ajac.infrastructure.api.processors;

public interface Executor <R, K> {
    R execute(K request);
}
