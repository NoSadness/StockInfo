package org.tokenring.analysis;

public class AnalyzeMACD implements AnalyzeHistory {
	StockHistory stockHistory;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public AnalyzeMACD(StockHistory StockHistory){
		stockHistory = StockHistory;
		stockHistory.calcMACD();
	}
	@Override
	public Event doAnalzy(int idx) {
		// TODO Auto-generated method stub
		StockExchangeData sedToday = stockHistory.getHisDataByExDate(idx);
		StockExchangeData sedYesterday = stockHistory.getHisDataByExDate(idx + 1);
		
		if ((sedYesterday != null) && (sedToday != null)){
			Double deltaDIF = sedToday.getDIF() - sedYesterday.getDIF();
			Double deltaDEA = sedToday.getDEA() - sedYesterday.getDEA();
			
			boolean isCrossed = ((sedToday.getDIF() - sedToday.getDEA()) * (sedYesterday.getDIF() - sedYesterday.getDEA())) < 0;
			
			if (isCrossed){
				if (deltaDIF > deltaDEA){
					//����
					//if ((sedToday.getDIF() > 0) && (sedToday.getDEA() > 0) && ((Math.atan(deltaDIF) - Math.atan(deltaDEA))>(15*Math.PI/180) )){
					/*
					if ((Math.atan(deltaDIF) - Math.atan(deltaDEA))>(15*Math.PI/180)) {
						return new Event(stockHistory.getStockID(),
								stockHistory.getStockBelong(),
								stockHistory.getStockName(),
								"MACD ����ͻ�� 15%",
								idx,
								stockHistory.getHisDataByExDate(idx).getExDate());
					}
					*/
					//����ͻ��
					return new Event(stockHistory.getStockID(),
							stockHistory.getStockBelong(),
							stockHistory.getStockName(),
							"MACD ����ͻ��",
							idx,
							stockHistory.getHisDataByExDate(idx).getExDate());
				}else{
					//����ͻ��
					return new Event(stockHistory.getStockID(),
							stockHistory.getStockBelong(),
							stockHistory.getStockName(),
							"MACD ����ͻ��",
							idx,
							stockHistory.getHisDataByExDate(idx).getExDate());
				}
			}
		}
		return null;
	}

}
