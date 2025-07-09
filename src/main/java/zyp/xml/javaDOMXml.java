package zyp.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class javaDOMXml {

    public static void main(String[] args) {
        try {
            // 创建 DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建 DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 解析 XML 文件
            Document document = builder.parse(new File("D:\\ZYPProject\\MyBatisDemo\\src\\main\\java\\zyp\\xml\\simple.xml"));
            document.getDocumentElement().normalize();

            // 获取根节点
            Element root = document.getDocumentElement();
            System.out.println("Root element: " + root.getNodeName());

            // 获取所有 book 元素
            NodeList nodeList = document.getElementsByTagName("book");

            // 遍历 book 元素
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) node;
                    String id = bookElement.getAttribute("id");
                    String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = bookElement.getElementsByTagName("author").item(0).getTextContent();

                    System.out.println("Book ID: " + id);
                    System.out.println("Title: " + title);
                    System.out.println("Author: " + author);
                    System.out.println("--------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
