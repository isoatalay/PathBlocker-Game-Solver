
package pathblocker;

public abstract class State {
    
    public abstract void doAction(int[] movement);
  
    public abstract boolean isGoal();
    
    abstract public State clone() throws CloneNotSupportedException;
    
    abstract public void undoAction();
     
     
}
