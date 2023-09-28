package com.example.application.exception;

public class FuncionarioException extends Exception
{
    public FuncionarioException(String message)
    {
        super(message);
    }

    public FuncionarioException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FuncionarioException(Throwable cause)
    {
        super(cause);
    }

}
