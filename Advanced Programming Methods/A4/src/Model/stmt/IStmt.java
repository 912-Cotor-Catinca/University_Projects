package Model.stmt;

import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    String toString();
}
