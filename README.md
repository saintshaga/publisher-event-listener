# Pure Publisher-Event-Listener library

Spring's ApplicationListener, ApplicationEvent and ApplicationPublisher is a useful tool of observer patten. 
However, we must depend on spring-context to use this tool.

Guava also has a similar tool EventBus. It has the same function. 
It's also convenient to use Guava's `EventBus` in most case.
In EventBus, the type of event, listener is not restricted. 

# How to use:

* In library without spring:

```
class CustomEvent extends BusinessEvent{}

BusinessEventInstanceFactory.getPublisher().publish(customEvent)
```

* In library with spring:

```
@Configuration
public class ObserverConfiguration {
    @Autowired
    private List<BusinessListener> listeners;

    public BusinessPublisher businessPublisher() {
        if(listeners != null) {
            listeners.forEach(listener -> 
                    BusinessEventInstanceFactory.getMulticaster()
                    .addBusinessListener(listener));
        }
        return BusinessEventInstanceFactory.getPublisher();
    }
    
}
```
Then when need to publish event:
```
public class SomeClass {
    @Autowired
    private BusinessPublisher publisher;
    
    public void someMethod() {
        ...
        publisher.publish(event);
        ...
    }
}
```