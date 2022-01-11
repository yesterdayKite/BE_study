package chap07;

public class ExeTimeCalculator {
    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate){
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num){
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.println("%s.factorial(%d) 실행시간 = %d\n", delegate.getClass().getSimpleName(),
                num, (end-start));
        return result;
    }
}
