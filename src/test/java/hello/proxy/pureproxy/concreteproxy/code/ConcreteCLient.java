package hello.proxy.pureproxy.concreteproxy.code;

public class ConcreteCLient {

    private ConcreteLogic concreteLogic;

    public ConcreteCLient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }


    public void execute(){
        concreteLogic.operation();
    }
}
