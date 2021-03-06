package dms.sc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import junit.framework.TestCase;
import nexcore.framework.bat.test.BatchTester;

/**
 * <ul>
 * <li>업무 그룹명 : dms/시스템공통 </li>
 * <li>서브 업무명 : BSC001 </li>
 * <li>설 명 : </li>
 * <li>작성일 : 2015-07-29T10:17:57</li>
 * <li>작성자 : 이영진 (newnofixing)</li>
 * </ul>
 */
public class BSC001Test extends TestCase {
    private BatchTester          tester;

    protected void setUp() throws Exception { 
        super.setUp();
        tester = new BatchTester(8125);
		setSystemProperties();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        tester.close();
    }

    private String getYYYYMMDD() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String getJobId() {
        return "J"+("BSC001".substring(1)) + "01";
    }

    private String getJobInsId() {
        return getJobId()+getYYYYMMDD()+"0001";
    }

    private int getJobExeSeqNum() {
        int eidBase = 0;

        File f = new File("./test/"+getJobInsId()+".eid");
        if (f.exists()) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(f));
                String line = in.readLine();
                eidBase = Integer.parseInt(line);
            }catch(Exception e) {
            }finally {
                try { in.close(); }catch(Exception ignore) {}
            }
        }else {
            f.getParentFile().mkdirs();
        }

        eidBase++;
        PrintWriter out = null;
        try {
            out = new PrintWriter(f);
            out.println(eidBase);
        } catch (Exception e) {
        } finally {
            try { out.close(); }catch(Exception ignore) {}
        }
        return eidBase;
    }

    private String getJobExeId() {
        DecimalFormat df = new DecimalFormat("000000");
        return getJobInsId()+df.format(getJobExeSeqNum());
    }
	
    /**
     * 테스트 메인 메소드
     */
    @Test
    public void testBatch() throws Exception {
		/* 배치 Job 실행 */
        String jobId      = getJobId();
        String jobInsId   = getJobInsId();
        String jobExeId   = getJobExeId();
        String jobType    = "EJB";
        String directory  = null;
        String className  = "dms.sc.BSC001";
        String procDate   = getYYYYMMDD();
        String baseDate   = getYYYYMMDD();
        String operatorId = "BATUSER";    // 7자리 사용아이디
        String operatorIp = "127.0.0.1";
        
        Map<String, String> inParameters = new HashMap<String, String>();
        setGlobalParameters(inParameters);
        setLocalParameters(inParameters);

        System.out.println("["+className+"] starting.");
        tester.start(jobId, jobInsId, jobExeId, jobType, directory, className, procDate, baseDate, operatorId, operatorIp, inParameters);

        // Job 로그 파일 sysout 으로 redirect
        tester.readJobLogTo(procDate, jobId, jobInsId, jobExeId, System.out);
    }
    
	/**
	 * 시스템 속성 설정
	 */
	private void setSystemProperties() throws IOException {
		System.setProperty("NEXCORE_HOME", new File("../runtime").getCanonicalPath());
		System.setProperty("system.id", "localhost");
	}
	
	/**
	 * 전역 파라미터 설정
	 */
	private void setGlobalParameters(Map<String, String> inParameters){
		inParameters.put("DATA_ROOT", "${NEXCORE_HOME}/dat"); // 데이타 파일 root dir
		inParameters.put("STAGE_CODE", "L");                  // 'D':개발, 'T':테스트, 'R':운영, 'L':로컬
	}
	
	/**
	 * 개별 파라미터 설정
	 */
	private void setLocalParameters(Map<String, String> inParameters){
        inParameters.put("FILE", "c:/projects/file.dat");
		//TODO 개별  파라미터 설정
        //inParameters.put("PARAM", "VALUE");
	}
}