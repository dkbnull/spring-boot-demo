package cn.wbnull.springbootdemo.boot;

/**
 * 全局异常
 *
 * @author dukunbiao(null)  2018-09-23
 * https://github.com/dkbnull/SpringBootDemo
 */
public class GlobalException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String code;
    private String message;

    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException(String code, String message) {
        super(code + ": " + message);
        this.code = code;
        this.message = message;
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public GlobalException(Throwable throwable) {
        super(throwable);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
