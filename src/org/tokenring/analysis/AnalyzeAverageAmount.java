package org.tokenring.analysis;

public class AnalyzeAverageAmount implements AnalyzeHistory {
	int averageDays;
	int continueDays;
	boolean isBelow;
	StockHistory stockHistory;
	/**
	 * @param AverageDays   ���������
	 * @param ContinueDays  ����������
	 * @param IsBelow       True ���ڣ�False ����
	 */
	public AnalyzeAverageAmount(int AverageDays,int ContinueDays,boolean IsBelow,StockHistory StockHistory){
		averageDays = AverageDays;
		continueDays = ContinueDays;
		isBelow = IsBelow;
		stockHistory = StockHistory;
	}
	@Override
	public Event doAnalzy(int idx) {
		// TODO Auto-generated method stub
		boolean result = true;
		int i = 0;
		StockExchangeData sed;
		while ((result) && i < continueDays){
			stockHistory.calcAverageAmount(idx + i, averageDays);
			sed = stockHistory.getHisDataByExDate(idx + i);
			if (sed.getAverageAmount() < 0){
				result = false;
				break;
			}
			if (isBelow){
				result = sed.getExAmount()< sed.getAverageAmount();
			}else{
				result = sed.getExAmount() > sed.getAverageAmount();
			}
			
			i ++;
		}
		
		if (result){
			//������������
			StringBuffer sb = new StringBuffer();
			sb.append("����");
			sb.append(continueDays);
			if (isBelow){
				sb.append("����");
			}else{
				sb.append("����");
			}
			sb.append(averageDays);
			sb.append("�վ��ɽ���");
			return new Event(stockHistory.getStockID(),
					stockHistory.getStockBelong(),
					stockHistory.getStockName(),
					sb.toString(),
					idx,
					stockHistory.getHisDataByExDate(idx).getExDate());
		}else{
			return null;
		}
	}

}
