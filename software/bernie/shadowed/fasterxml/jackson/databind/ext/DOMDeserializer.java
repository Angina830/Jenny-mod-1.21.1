/*    */ package software.bernie.shadowed.fasterxml.jackson.databind.ext;
/*    */ 
/*    */ import java.io.StringReader;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.xml.sax.InputSource;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
/*    */ import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DOMDeserializer<T>
/*    */   extends FromStringDeserializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final DocumentBuilderFactory DEFAULT_PARSER_FACTORY;
/*    */   
/*    */   static {
/* 28 */     DocumentBuilderFactory parserFactory = DocumentBuilderFactory.newInstance();
/*    */     
/* 30 */     parserFactory.setNamespaceAware(true);
/*    */     
/* 32 */     parserFactory.setExpandEntityReferences(false);
/*    */     
/*    */     try {
/* 35 */       parserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/* 36 */     } catch (ParserConfigurationException pce) {
/*    */     
/* 38 */     } catch (Error e) {}
/*    */ 
/*    */ 
/*    */     
/* 42 */     DEFAULT_PARSER_FACTORY = parserFactory;
/*    */   }
/*    */   protected DOMDeserializer(Class<T> cls) {
/* 45 */     super(cls);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Document parse(String value) throws IllegalArgumentException {
/*    */     try {
/* 52 */       return documentBuilder().parse(new InputSource(new StringReader(value)));
/* 53 */     } catch (Exception e) {
/* 54 */       throw new IllegalArgumentException("Failed to parse JSON String as XML: " + e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected DocumentBuilder documentBuilder() throws ParserConfigurationException {
/* 65 */     return DEFAULT_PARSER_FACTORY.newDocumentBuilder();
/*    */   }
/*    */   
/*    */   public abstract T _deserialize(String paramString, DeserializationContext paramDeserializationContext);
/*    */   
/*    */   public static class NodeDeserializer
/*    */     extends DOMDeserializer<Node>
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public NodeDeserializer() {
/* 76 */       super(Node.class);
/*    */     }
/*    */     public Node _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
/* 79 */       return parse(value);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class DocumentDeserializer extends DOMDeserializer<Document> {
/*    */     public DocumentDeserializer() {
/* 85 */       super(Document.class);
/*    */     } private static final long serialVersionUID = 1L;
/*    */     public Document _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
/* 88 */       return parse(value);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\zakur\Downloads\Jenny-Mod-Forge-1.12.2-v1.8.0.jar!\software\bernie\shadowed\fasterxml\jackson\databind\ext\DOMDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */