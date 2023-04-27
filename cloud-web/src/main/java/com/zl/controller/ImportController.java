package com.zl.controller;


import com.zl.sys.controller.config.enums.DownloadTemplate;
import com.zl.sys.service.SLogOperatorallService;
import com.zl.utils.DateUtils;
import com.zl.utils.ExcelExport;
import com.zl.utils.UUIDUtil;
import com.zl.utils.Write2007Excel;
import com.zl.vo.HouseInfoVo;
import com.zl.vo.LogVo;
import com.zl.vo.Result;
import com.zl.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/import")
public class ImportController {

    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Value("${application.excel.upload-dir}")
    private String excelUploadDir;

//    @Resource
//    private ImportService importService;
//    @Resource
//    private HouseInfoService houseInfoService;
    @Resource
    private SLogOperatorallService sLogOperatorallService;

    //下载导入模板
    @GetMapping(value = "/downloadExcelFileMb/{tid}")
    public void downloadExcelFileMb(HttpServletRequest request, HttpServletResponse response,@PathVariable("tid") String tid)  {
        System.out.println(tid);
        XSSFWorkbook wb = new XSSFWorkbook();
        Write2007Excel we = new Write2007Excel();
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        if (tid.equals(DownloadTemplate.HOUSE_IMPORT.getTid())){
            String[] tops = DownloadTemplate.HOUSE_IMPORT.getTitle().split(",");
            wb = we.addSheet(wb, tops, DownloadTemplate.HOUSE_IMPORT.getName());

            try {
                String name = DownloadTemplate.HOUSE_IMPORT.getName()+"_模板.xlsx";
                String encode = URLEncoder.encode(name, "UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename= "+encode+"" );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            wb.write(os);
            assert os != null;
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //excel导入
    @PostMapping(value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传路径
        String filePath = excelUploadDir + "/" + DateUtils.getYearMonth() + "/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        String rfileName = UUIDUtil.getUUID() + suffixName;
        File dest = new File(filePath + rfileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        if (file.isEmpty()) {
            return Result.error().message("文件为空");
        }

        try {
            file.transferTo(dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok().data("filename",fileName).data("url",filePath+rfileName);
    }



    @RequestMapping(value = "/downloadHouseData/{tid}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadDealMessage(HttpServletResponse response, @RequestBody SearchVo searchVo,@PathVariable("tid") String tid) {
        if ("40287c5d748a904a01748b716a630101".equals(tid)){
//            List<HouseInfoVo> houseInfos = houseInfoService.downloadHouseData(searchVo);
            try {
                String[] headers = {"住户ID", "住户名称", "所属镇", "所属村", "所属组", "环境卫生积分", "移风易俗积分", "森林队灭火积分", "志愿服务积分", "总积分"};
                String[] keyStrings = {"id", "houseName", "townName", "villName", "teamName", "environment", "custom", "finghtingFire", "volunteer", "sun"};
                String date = DateUtils.getDate();
                String encode = URLEncoder.encode("户导出"+date+".xlsx", "UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename= "+encode+"" );
                response.setContentType("application/vnd.ms-excel");
                ExcelExport ee = new ExcelExport<>();
//                ee.exportExcel2007("户导出", headers, houseInfos, response.getOutputStream(), keyStrings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if ("8644620b32a04290b573a8b9ce5e5b81".equals(tid)){
            List<LogVo> logVos = sLogOperatorallService.downloadLogsData(searchVo);
            String[] headers = {"编号","uri", "method", "params", "createDate", "beanName", "beanMethod", "beginTime", "endTime", "requestTime","result","url","osInfo","browserInfo"};
            String[] keyStrings = {"id", "uri", "method", "params", "createDate", "beanName", "beanMethod", "beginTime", "endTime", "requestTime","result","url","osInfo","browserInfo"};
            String date = DateUtils.getDate();
            try {
            String encode = URLEncoder.encode("日志导出"+date+".xlsx", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename= "+encode+"" );
            response.setContentType("application/vnd.ms-excel");
            ExcelExport ee = new ExcelExport<>();
            ee.exportExcel2007("户导出", headers, logVos, response.getOutputStream(), keyStrings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @PostMapping("/downloadFile")
    @GetMapping("/downloadFile")
    public void downloadFile(String filePath,String fileName, HttpServletResponse response) {
        System.out.println(filePath);
        System.out.println(fileName);
//    public void downloadFile(HttpServletResponse response) {

        //获取文件路径
        //替换目录符，匹配Linux目录
//        String newFilePath = downLoadFileVo.getFilePath().replaceAll("\\\\", "/");
//        String path="C:\\excel\\202211\\16995b9dbc164e6b94a50d14d06ede08.xlsx";
        String newFilePath = filePath.replaceAll("\\\\", "/");
        System.out.println(newFilePath);
        //创建不同的文件夹目录
        File file = new File(newFilePath);
        // 判断文件是否存在
        if (file.exists() && StringUtils.isNotBlank(newFilePath)) {
            try (InputStream in = new FileInputStream(newFilePath);
                 BufferedInputStream bis = new BufferedInputStream(in);
                 OutputStream out = response.getOutputStream();
                 BufferedOutputStream bos = new BufferedOutputStream(out)) {
                //查询文件名
//                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downLoadFileVo.getFileName(), "UTF-8"));
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/vnd.ms-excel");
                byte[] data = new byte[1024];
                int bytes = 0;
                while ((bytes = bis.read(data, 0, data.length)) != -1) {
                    bos.write(data, 0, bytes);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
