package backend.xml;
import java.util.ArrayList;
import java.util.List;

public class Save<Element> {
    protected List<Object> buffer;

    public Save() {
        buffer = new ArrayList<>();
    }

    public void addBuffer(Element element) {
        buffer.add(element);
    }

    public List<Object> getBuffer(){
        return buffer;
    }

    public void doSave() {
        XmlWriter.writeInXml(buffer);
        buffer.clear();
    }

}
