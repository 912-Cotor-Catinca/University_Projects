package model.statements;

import exceptions.FileException;
import exceptions.MyException;
import exceptions.TypeMismatchException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        IValue value = exp.eval(symTable, heapTable);
        if (value.getType().equals(new StringType())) {
            StringValue stringValue = (StringValue) value;
            if (fileTable.isDefined(stringValue)) {
                BufferedReader bufferedReader = fileTable.lookup(stringValue);
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    throw new FileException(String.format("File could not be closed: %s", exception.getMessage()));
                }
                fileTable.remove(stringValue);
            } else {
                throw new FileException("File to close does not exist!");
            }
        } else {
            throw new FileException("File name not of string type!");
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new TypeMismatchException("File name not of string type!");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp);
    }

    @Override
    public String toString() {
        return String.format("closeRFile(%s)", exp.toString());
    }
}
