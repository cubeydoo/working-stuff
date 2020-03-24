import java.util.List;
import java.util.ArrayList;

/** A set of String values.
 *  @author Tyler Rathkamp
 */
class ECHashStringSet implements StringSet {
    private ArrayList<String>[] _buckets = new ArrayList[4];
    private int numElem = 0;
    public ECHashStringSet() {
        for (int i = 0; i <= 3; i++) {
            _buckets[i] = new ArrayList<String>();
        }
    }

    public void resize() {
        ArrayList<String>[] oldBuckets = _buckets;
        ArrayList<String>[] newBuckets = new ArrayList[_buckets.length * 2];
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new ArrayList<String>();
        }
        _buckets = newBuckets;
        for (int i = 0; i < oldBuckets.length; i++) {
            for (int x = 0; x < oldBuckets[i].size(); x++) {
                put(oldBuckets[i].get(x));
            }
        }
    }

    @Override
    public void put(String s) {
        int index = s.hashCode();
        if (index < 0) {
            index = (index & 0x7fffffff) % _buckets.length;
        } else {
            index = index % _buckets.length;
        }
        _buckets[index].add(s);
        numElem += 1;
        if (numElem / _buckets.length > 5) {
            resize();
        }
    }

    @Override
    public boolean contains(String s) {
        int index = s.hashCode();
        if (index < 0) {
            index = (index & 0x7fffffff) % _buckets.length;
        } else {
            index = index % _buckets.length;
        }
        return _buckets[index].contains(s);
    }

    @Override
    public List<String> asList() {
        ArrayList<String> returnMe = new ArrayList<>();
        for (int i = 0; i < _buckets.length; i++) {
            returnMe.addAll(_buckets[i]);
        }
        return returnMe;
    }
}
