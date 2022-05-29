package entities;

public interface  FunctionThrow <T,R>{

	R apply(T t) throws Exception;

	//Object apply(T t);
}
