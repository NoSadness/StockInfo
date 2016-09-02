package org.tokenring.analysis;

/**
 * @author XiePingfan
 *
 */
public class AnalyzePriceAndAmountPastDaysContinueRiseRate implements AnalyzeHistory {
	int days;
	int riseRate;
	
	StockHistory stockHistory;

	/**
	 * 
	 * @param Days
	 * @param RiseRate ����������ǣ�RiseRate ���� 1��������������RiseRate����-1
	 * @param idx
	 * @param StockHistory
	 */
	public AnalyzePriceAndAmountPastDaysContinueRiseRate(int Days, int RiseRate,  StockHistory StockHistory) {
		//����������ǣ�RiseRate ���� 1��������������RiseRate����-1
		days = Days;
		riseRate = RiseRate;
	
		stockHistory = StockHistory;
	}

	@Override
	public Event doAnalzy(int idx) {
		// TODO Auto-generated method stub
		// ����Ƿ�����x�����y%���Ҽ۸�����

		StockExchangeData sedBase = stockHistory.getHisDataByExDate(idx);
		StockExchangeData sedPast = stockHistory.getHisDataByExDate(idx + days);
		String exDate = sedBase.getExDate();
		Event result = null;
		if ((sedBase != null) && (sedPast != null)) {
			int baseExAmount;
			int pastExAmount;
			
			double baseEndPrice;
			double pastEndPrice;
			
			boolean isRise = true;
			for (int i = 1; isRise && (i <= days); i++) {
				sedBase = stockHistory.getHisDataByExDate(idx + i - 1);
				sedPast = stockHistory.getHisDataByExDate(idx + i);

				baseExAmount = sedBase.getExAmount();
				pastExAmount = sedPast.getExAmount();
				baseEndPrice = sedBase.getEndPrice();
				pastEndPrice = sedPast.getEndPrice();

				if (riseRate == 1){
					isRise = (baseExAmount > pastExAmount) && (baseEndPrice > pastEndPrice);
				}else{
					isRise = (baseExAmount < pastExAmount) && (baseEndPrice < pastEndPrice);
				}
				
			}

			if (isRise) {
				StringBuffer sb = new StringBuffer();
				sb.append("���� ");
				sb.append(days);
				if (riseRate == 1) {
					sb.append("����������");
					
				} else {
					sb.append("���������");
					
				}
				
				result = new Event(stockHistory.getStockID(), stockHistory.getStockBelong(),
						stockHistory.getStockName(), sb.toString(), idx,exDate);

			}
		}
		return result;
	}
}
