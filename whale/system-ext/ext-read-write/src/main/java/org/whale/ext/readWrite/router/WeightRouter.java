package org.whale.ext.readWrite.router;

import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.whale.system.common.util.SpringContextHolder;

/**
 * 随机，按权重设置随机概率。
 * 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
 * 
 * @author 王金绍
 *
 */
public class WeightRouter extends AbstractRouter {
	
	private Map<String, Integer> weightMap;
	
	private RoundRobinRouter pollRouter;
	
	private Object lock = new Object();
	
	private final Random random = new Random();

	public Map<String, Integer> getWeightMap() {
		return weightMap;
	}

	public void setWeightMap(Map<String, Integer> weightMap) {
		this.weightMap = weightMap;
	}

	@Override
	public DataSource doSelect(DataSource writeDataSource, DataSource[] readDataSources, String[] readDataSourceNames) {
		if(weightMap == null || weightMap.size() < 1){
			return this.pollSelect(writeDataSource, readDataSources, readDataSourceNames);
		}
		
		int length = readDataSources.length; // 总个数
        int totalWeight = 0; // 总权重
        boolean sameWeight = true; // 权重是否都一样
        for (int i = 0; i < length; i++) {
            int weight = weightMap.get(readDataSourceNames[i]);
            totalWeight += weight; // 累计总权重
            if (sameWeight && i > 0 && weight !=  weightMap.get(readDataSourceNames[i-1])) {
                sameWeight = false; // 计算所有权重是否一样
            }
        }
        if (totalWeight > 0 && ! sameWeight) {
            // 如果权重不相同且权重大于0则按总权重数随机
            int offset = random.nextInt(totalWeight);
            // 并确定随机值落在哪个片断上
            for (int i = 0; i < length; i++) {
                offset -= weightMap.get(readDataSourceNames[i]);
                if (offset < 0) {
                    return readDataSources[i];
                }
            }
        }
		
        //相同，使用轮询
        return this.pollSelect(writeDataSource, readDataSources, readDataSourceNames);
	}
	
	private DataSource pollSelect(DataSource writeDataSource, DataSource[] readDataSources, String[] readDataSourceNames){
		if(pollRouter == null){
			synchronized (lock) {
				if(pollRouter == null){
					try{
						pollRouter = SpringContextHolder.getBean(RoundRobinRouter.class);
					}catch(Exception e){
						pollRouter = new RoundRobinRouter();
					}
				}
			}
		}
		return pollRouter.route(writeDataSource, readDataSources, readDataSourceNames);
	}
}
