package store.novabook.front.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogCrash {
	private static final Logger USER_LOG = LoggerFactory.getLogger("user-logger");

	public void logging() {
		USER_LOG.debug("LogNCrash Debug.") ;

		// Log & Crash에서 예약된 항목 이외, 사용자 정의 항목 사용 시 MDC 활용
		MDC.put("userid", "nhnent-userId");
		USER_LOG.warn("Customize items...") ;
		MDC.clear();

		try {
			String logncrash = null;
			if(true) {
				System.out.print(logncrash.toString());
			}
		} catch(Exception e) {
			USER_LOG.error("LogNCrash Exception.", e);
		}
	}
}
