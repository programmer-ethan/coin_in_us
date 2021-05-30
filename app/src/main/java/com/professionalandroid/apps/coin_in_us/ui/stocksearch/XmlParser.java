package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {
    public static String[][] xmlparse(String xml) {
        String[][] result = null;
        try {
            if (xml !=null) {
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                InputStream is = new ByteArrayInputStream(xml.getBytes());
                // 파싱 시작
                Document doc = documentBuilder.parse(is);
                // 최상위 노드 찾기
                Element element = doc.getDocumentElement();
                // 원하는 태그 데이터 찾아오기
                NodeList engNameList = element.getElementsByTagName("engSecnNm");
                NodeList korNameList = element.getElementsByTagName("korSecnNm");
                NodeList kindOfList = element.getElementsByTagName("secnKacdNm");
                // 데이터 개수 찾이오기
                int n = korNameList.getLength();
                result = new String[n][3];
                for (int i = 0; i < n; i++) {

                    Node item = engNameList.item(i);
                    Node text = item.getFirstChild();
                    String itemValue = text.getNodeValue();
                    result[i][0] = itemValue;

                    Node item1 = korNameList.item(i);
                    Node text1 = item1.getFirstChild();
                    String itemValue1 = text1.getNodeValue();
                    result[i][1] = itemValue1;

                    Node item2 = kindOfList.item(i);
                    Node text2 = item2.getFirstChild();
                    String itemValue2 = text2.getNodeValue();
                    result[i][2] = itemValue2;
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
