package com.sorint.demo;

import com.hazelcast.client.config.ClientClasspathXmlConfig;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.client.HazelcastClient;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String xmlPath = "hazelcast-client.xml";
        ClientConfig clientConfig = new ClientClasspathXmlConfig(xmlPath);
        HazelcastInstance instance = HazelcastClient.newHazelcastClient(clientConfig);

        Map<Object, Object> ticketMap = instance.getMap("service/ticket");
        System.out.println("Ticket map size: " + ticketMap.size());

    }
}
