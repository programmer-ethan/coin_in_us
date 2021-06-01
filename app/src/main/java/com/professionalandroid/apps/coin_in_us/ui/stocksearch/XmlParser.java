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
                NodeList item1 = element.getElementsByTagName("korSecnNm");
                NodeList item2 = element.getElementsByTagName("engSecnNm");
                NodeList item3 = element.getElementsByTagName("secnKacdNm");
                NodeList item4 = element.getElementsByTagName("issucoCustno");
                NodeList item5 = element.getElementsByTagName("isin");
                NodeList item6 = element.getElementsByTagName("issuDt");
                NodeList item7 = element.getElementsByTagName("shotnIsin");
                // 데이터 개수 찾이오기
                int n = item1.getLength();
                result = new String[n][7];
                for (int i = 0; i < n; i++) {

                    Node item = item1.item(i);
                    Node text = item.getFirstChild();
                    String itemValue = text.getNodeValue();
                    result[i][0] = itemValue;

                    item = item2.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][1] = itemValue;

                    item = item3.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][2] = itemValue;

                    item = item4.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][3] = itemValue;

                    item = item5.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][4] = itemValue;

                    item = item6.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][5] = itemValue;

                    item = item7.item(i);
                    text = item.getFirstChild();
                    itemValue = text.getNodeValue();
                    result[i][6] = itemValue;

                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
