/*
Rohan Patel
RVP3
382-7175
*/
import java.io.Serializable;

@SuppressWarnings("unchecked")
public class SimpleStack<T> implements Serializable, SimpleStackInterface<T>{
	private T[] arr;
	private int current = 0; //index of top item
	public SimpleStack (int size){
		arr = (T[]) new Object[size];
		//arr = (T[])Array.newInstance(T.class,size);
	}

    public boolean add(T item){
    	if (current == arr.length){
    		return false;
    	}
    	arr[current++] = item; //will point to whatever is right after the top
    	return true;
    }

    public T remove(){
    	if (current == 0){
    		return null;
    	}
    	return arr[--current]; //will return what is right before the current
    	//does not actually remove it but since current is decremented the client will never have to access it
    }

    public Object[] topItems(int howMany){
    	if (howMany > current){
    		return null; //current is the length of the stack (one more than the index)
    	}
    	T[] top = (T[]) new Object [howMany]; //makes new Object[]
    	for(int i = 0; i < howMany; i++){
    		top[i] = arr[current-i-1]; //first thing will be current - 1 and then go down from there
    	}
    	return top;
    }

    public boolean contains(T item){
        boolean boo = false;
        for(int i = 0; i < arr.length; i++){
            if(item.equals(arr[i])){
                boo = true;
            }
        }
        return boo;
    }

    public boolean isEmpty(){
    	return current == 0; //return true is current is 0, empty
    }

    public boolean isFull(){
    	return current == arr.length;
    }

    public int size(){
    	return current;
    }
}