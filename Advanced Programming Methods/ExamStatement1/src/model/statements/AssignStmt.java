package model.statements;

import exceptions.MyException;
import exceptions.TypeMismatchException;
import exceptions.UndefinedException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IHeap;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        if (symTable.isDefined(id)) {
            IValue val = exp.eval(symTable, heapTable);
            IType type = (symTable.lookup(id)).getType();
            if (val.getType().equals(type)) {
                symTable.update(id, val);
            } else {
                throw new TypeMismatchException(String.format("Declared type of variable %s and type of assigned expression" +
                        "do not match!", id));
            }
        } else {
            throw new UndefinedException(String.format("%s has not been declared!", id));
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(id);
        IType typeExp = exp.typeCheck(typeEnv);
        if (typeVar.equals(typeExp)) {
            return typeEnv;
        } else {
            throw new TypeMismatchException(String.format("Declared type of variable %s and type of assigned expression" +
                    "do not match!", id));
        }
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", id, exp.toString());
    }
}
