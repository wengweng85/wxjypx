package com.insigma.mvc.controller.common.codevalidator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insigma.mvc.MvcHelper;
import com.insigma.resolver.AppException;

/**
 * Created by Administrator on 2015-01-14.
 */
@Controller
@RequestMapping(value = "/verifycode")
public class CodeValidatorController extends MvcHelper {


    Log log = LogFactory.getLog(CodeValidatorController.class);

    /**
     * ��֤������
     *
     * @param request
     * @param response
     * @return
     * @throws com.insigma.resolver.AppException
     */
    @SuppressWarnings("unused")
	@RequestMapping(value = "/create")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws AppException {
        //��ӦΪimage
        response.setContentType("image/jpeg");
        // ����Ҫ����ͼƬ�Ŀ�͸�
        int width = 90;
        int height = 30;
        // ���пɷ���ͼ�����ݻ�������Image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // ������������
        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(new Color(92,180,230));// ��ɫ
        g.fillRect(0, 0, width, height);// ȫͼ���Ʊ���ɫ
        // ��ͼƬ�ϻ���150��������
        //g.setColor(getColor(180, 200));// ��ɫ
        Random random = new Random();
		/*for (int i = 0; i < 150; i++) {
			// �����ߵ�����
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			// x��y����ƫ�ƶ��ٸ���λ
			int deltax = random.nextInt(6) + 1;
			int deltay = random.nextInt(12) + 1;
			// ����һ������ָ�����Ե�ʵ�ĵ� BasicStroke��2Dͼ�Σ�
			BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
			// Line2D��һ���޷�ֱ��ʵ�����ĳ�����
			Line2D line = new Line2D.Double(x, y, x + deltax, y + deltay);
			g2d.setStroke(bs);// ���� BasicStroke
			g2d.draw(line);// �ٻ�������
		}*/

        // �����Щ�ַ�,�Ա���֤
        String str = "";
        int temp = 0;
        Font font = new Font("Tahoma", Font.BOLD, 24);
        g.setFont(font);// ���û��ʻ��������ʽ
        // �����ĸ��ַ�
        for (int i = 0; i < 4; i++) {
            //if (random.nextInt(2) == 1) {
            temp = random.nextInt(26) + 'a';
			/*} else {
				temp = random.nextInt(10) + '0';
			}*/
            char ctemp = (char) temp;
            str += ctemp;
			/*Color c = getColor(20, 130);//��ɫ����
			g.setColor(c);
			*//** ******������������Ų���תһ���ĽǶ�****** *//*
			AffineTransform trans = new AffineTransform();
			//45���ȣ���ת��λ��
			trans.rotate(random.nextInt(45) * Math.PI / 180, 15 * i + 10, 6);
			// ��������
			float scaleSize = random.nextFloat() + 0.5f;//���Ŵ�С
			if (scaleSize < 0.8f || scaleSize > 1.2) {
				scaleSize = 1.0f;
			}
			trans.scale(scaleSize, scaleSize);
			g2d.setTransform(trans);
			//����ʲôλ��
			g.drawString(ctemp + "", 15 * i + 10, 14);*/

        }
        //Color c = getColor(20, 130);//��ɫ����
        g.setColor(Color.WHITE);
        g.drawString(str, 20, 20);
        //��ֹͼƬ��������ˢ�¾Ͳ������ظ���ͼƬ����
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        
        SecurityUtils.getSubject().getSession().setAttribute("session_validator_code",str);
        try {
            //�ѵõ�����֤�뱣���jpeg��ʽ������������
            ImageIO.write(image, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * ��֤��У��
     * @param request
     * @param response
     * @throws com.insigma.resolver.AppException
     */
    @RequestMapping(value = "/check")
    public void check(HttpServletRequest request ,HttpServletResponse response) throws  AppException{
        try{
        	String vaildatorCode =(String)request.getSession().getAttribute("session_validator_code");
            String verifycode = request.getParameter("verifycode");
            boolean result = false;
            // ��֤����ȷ
            if (verifycode.equalsIgnoreCase(vaildatorCode)) {
                result = true;
               //���session_validator_code
                request.getSession().removeAttribute("session_validator_code");
            }
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }catch (IOException e){
            throw new AppException(e);
        }
    }

    // �õ����й涨����ɫ
    public static Color getColor(int start, int end) {
        Random R = new Random();
        int r = start + R.nextInt(end - start);
        int g = start + R.nextInt(end - start);
        int b = start + R.nextInt(end - start);
        return new Color(r, g, b);
    }

}
