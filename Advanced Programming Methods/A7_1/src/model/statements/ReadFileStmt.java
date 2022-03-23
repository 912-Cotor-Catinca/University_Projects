package model.statements;

import exceptions.FileException;
import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    IExp exp;
    String name;

    public ReadFileStmt(IExp exp, String name) {
        this.exp = exp;
        this.name = name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        if (symTable.isDefined(name)) {
            IType type = symTable.lookup(name).getType();
            if (type.equals(new IntType())) {
                IValue value = exp.eval(symTable, heapTable);
                if (value.getType().equals(new StringType())) {
                    StringValue stringValue = (StringValue) value;
                    BufferedReader bufferedReader = fileTable.lookup(stringValue);
                    try {
                        String line = bufferedReader.readLine();
                        IntValue intValue;
                        if (line == null) {
                            intValue = new IntValue();
                        }
                        else {
                            intValue = new IntValue(Integer.parseInt(line));
                        }
                        symTable.update(name, intValue);
                    } catch (IOException exception) {
                        throw new FileException(String.format("Cannot read from file: %s", exception.getMessage()));
                    }
                } else {
                    throw new TypeMismatchException(String.format("%s is not of type string!", value));
                }
            } else {
                throw new TypeMismatchException(String.format("%s is not of type int!", name));
            }
        } else {
            throw new UndefinedException(String.format("%s has not been declared!", name));
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typeCheck(typeEnv);
        IType typeVar = typeEnv.lookup(name);
        if (typeExp.equals(new StringType())) {
            if (typeVar.equals(new IntType())) {
                return typeEnv;
            } else {
                throw new TypeMismatchException("Variable used to store the file read must be of integer type!");
            }
        } else {
            throw new TypeMismatchException("Name of the file to read must be of string type!");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp, name);
    }

    @Override
    public String toString() {
        return String.format("readFile(%s, %s)", exp.toString(), name);
    }
}
