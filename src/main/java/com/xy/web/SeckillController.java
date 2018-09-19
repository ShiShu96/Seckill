package com.xy.web;

import com.xy.dto.Exposer;
import com.xy.dto.SeckillExecution;
import com.xy.dto.SeckillResult;
import com.xy.entity.Seckill;
import com.xy.enums.SeckillStatEnum;
import com.xy.exception.RepeatKillException;
import com.xy.exception.SeckillCloseException;
import com.xy.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

   @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
       if (seckillId == null)
           return "redirect:/seckill/list";
       Seckill seckill = seckillService.getById(seckillId);
       if (seckill == null)
           return "forward:/seckill/list";

       model.addAttribute("seckill", seckill);
       return "detail";
   }


    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);

        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable Long seckillId,
                                                     @PathVariable String md5,
                                                     @CookieValue(value = "killPhone", required = false) Long userPhone){
        if(userPhone==null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        try{
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return  new SeckillResult<SeckillExecution>(true, execution);

        }catch (RepeatKillException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL );
            return new SeckillResult<SeckillExecution>(true, execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e){
            logger.info(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, execution);
        }


    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){

        Date date = new Date();
        Long time = date.getTime();
        //true or false?????
        return new SeckillResult<Long>(true, time);

    }


}
