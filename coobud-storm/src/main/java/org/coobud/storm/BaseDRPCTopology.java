package org.coobud.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BaseDRPCTopology {
	
	public static class ExclaimBolt extends BaseBasicBolt{

		public void execute(Tuple tuple, BasicOutputCollector collector) {
			String input=tuple.getString(1);
			collector.emit(new Values(tuple.getValue(0),input+"!"));
		}
		

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("id","result"));
		}
		
		
		
	}

}
