/*
 * ********************************************************************************************************************
 *  <p/>
 *  BACKENDLESS.COM CONFIDENTIAL
 *  <p/>
 *  ********************************************************************************************************************
 *  <p/>
 *  Copyright 2012 BACKENDLESS.COM. All Rights Reserved.
 *  <p/>
 *  NOTICE: All information contained herein is, and remains the property of Backendless.com and its suppliers,
 *  if any. The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 *  suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 *  or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 *  unless prior written permission is obtained from Backendless.com.
 *  <p/>
 *  ********************************************************************************************************************
 */

import com.backendless.Backendless;
import com.backendless.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class LoggingDemo
{
  public static void main( String[] args ) throws Exception
  {
    Backendless.setUrl( "http://localhost:9000" );
    Backendless.initApp( "35B7BDE7-B92F-F8F4-FF6C-880E13A09E00", "69818C9F-EA7B-8AE0-FF59-6A98628FE800", "v1" );

    String xmlFile = "/Users/backendlessdev/IdeaProjects/logger/src/romeo-and-juliet.xml";
    File xmlSrc = new File( xmlFile );
    BufferedInputStream inputXml = new BufferedInputStream( new FileInputStream( xmlSrc ), 30 * 1024 );
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( inputXml );
    inputXml.close();

    XPath xpath = XPathFactory.newInstance().newXPath();
    NodeList speechItems = (NodeList) xpath.evaluate( "//SPEECH", doc, XPathConstants.NODESET );
    Random random = new Random();

    Backendless.Logging.setLogReportingPolicy( 1, 1 );
    Logger logger = Backendless.Logging.getLogger( "logger" );
    //logger.error("error");
    logger.info( "mess" );

    System.out.println(logger);


    while( true )
    {
      int index = random.nextInt( speechItems.getLength() - 1 );
      org.w3c.dom.Node speechItem = speechItems.item( index );

      Node speakerNode = (Node) xpath.evaluate( "SPEAKER", speechItem, XPathConstants.NODE );
      Node lineNode = (Node) xpath.evaluate( "LINE", speechItem, XPathConstants.NODE );

      String speaker = speakerNode.getTextContent();
      String line = lineNode.getTextContent();
      logger = Backendless.Logging.getLogger( speaker );

      if( speaker.equals( "ROMEO" ) || speaker.equals( "JULIET" ) )
        logger.fatal( line );
      else
        logger.error( line );

      System.out.println( speaker + ": " + line );
      Thread.sleep( 1000 );
    }
  }
}
