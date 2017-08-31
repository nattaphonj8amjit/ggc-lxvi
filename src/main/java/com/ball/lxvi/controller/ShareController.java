package com.ball.lxvi.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/share")
public class ShareController {

	private static List<Double> group1 = new LinkedList<>();
	static {
		group1.add(Double.valueOf(1230));
		group1.add(Double.valueOf(1300));
		group1.add(Double.valueOf(1450));
		group1.add(Double.valueOf(1650));
		group1.add(Double.valueOf(1150));
		group1.add(Double.valueOf(1310));
		group1.add(Double.valueOf(1420));
		group1.add(Double.valueOf(890));
		group1.add(Double.valueOf(850));
		group1.add(Double.valueOf(500));
		group1.add(Double.valueOf(700)); // random
		group1.add(Double.valueOf(350)); // toe
		group1.add(Double.valueOf(400)); // maxy
		group1.add(Double.valueOf(590)); // natty mother
		group1.add(Double.valueOf(550)); // nok
		group1.add(Double.valueOf(400)); // tei
		group1.add(Double.valueOf(350)); // next

	}

	private static List<Double> group2 = new LinkedList<>();
	static {
		group2.add(Double.valueOf(1190));
		group2.add(Double.valueOf(1450));
		group2.add(Double.valueOf(1520));
		group2.add(Double.valueOf(1300));
		group2.add(Double.valueOf(1460));
		group2.add(Double.valueOf(1500));
		group2.add(Double.valueOf(1380));
		group2.add(Double.valueOf(1500));
		group2.add(Double.valueOf(1350));
		group2.add(Double.valueOf(400));
		group2.add(Double.valueOf(450));// maxky
		group2.add(Double.valueOf(350));// nong p maxky
		group2.add(Double.valueOf(510));// pek
		group2.add(Double.valueOf(650));// natty
		group2.add(Double.valueOf(400));// pla
		group2.add(Double.valueOf(560)); // bank
		group2.add(Double.valueOf(350)); // bank

	}

	public double calculateTake(List<Double> interest) {

		int size = interest.size();
		double take = 0;
		if (interest.size() > 1) {
			take = (interest.stream().mapToInt(i -> i.intValue()).sum() - interest.get(size - 1));
		}

		return take;
	}

	int playerAmount = 30;
	double base = 2000;

	@Cacheable("share")
	@RequestMapping("/calculate")
	public String calculate(Model model) {
		ShareModel shareModelA = new ShareModel();
		shareModelA.setGroup("A");
		RoundModel roundModelA;
		List<Double> tempA = new LinkedList<>();
		for (int i = 0; i < group1.size(); i++) {
			roundModelA = new RoundModel();
			roundModelA.setRound(i + 1);
			tempA.add(group1.get(i));
			roundModelA.setInterest(group1.get(i));
			roundModelA.setBase(base);
			roundModelA.setTake((playerAmount * base) + calculateTake(tempA));
			roundModelA.setGive((base * 30) + ((group1.get(i)) * (playerAmount - (i + 1))));
			shareModelA.addRoundModels(roundModelA);
		}

		ShareModel shareModelB = new ShareModel();
		shareModelB.setGroup("B");
		RoundModel roundModelB;
		List<Double> tempB = new LinkedList<>();
		for (int i = 0; i < group2.size(); i++) {
			roundModelB = new RoundModel();
			roundModelB.setRound(i + 1);
			tempB.add(group2.get(i));
			roundModelB.setInterest(group2.get(i));
			roundModelB.setBase(base);
			roundModelB.setTake((playerAmount * base) + calculateTake(tempB));
			roundModelB.setGive((base * 30) + ((group2.get(i)) * (playerAmount - (i + 1))));
			shareModelB.addRoundModels(roundModelB);
		}

		shareModelA.sum();
		shareModelB.sum();
		model.addAttribute("A", shareModelA);
		model.addAttribute("B", shareModelB);
		return "demo";
	}

	public class ShareModel {
		public String group;
		public List<RoundModel> roundModels;
		public double totalBase;
		public double totalInterest;
		public double totalSum;
		public double next;

		public double getNext() {
			return next;
		}

		public void setNext(double next) {
			this.next = next;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public List<RoundModel> getRoundModels() {
			return roundModels;
		}

		public void addRoundModels(RoundModel roundModel) {
			if (roundModels == null) {
				this.roundModels = new LinkedList<RoundModel>();
			}
			this.roundModels.add(roundModel);
		}

		public void sum() {
			totalBase = 0;
			totalInterest = 0;
			totalSum = 0;

			for (RoundModel roundModel : roundModels) {
				totalBase += roundModel.getBase();
				totalInterest += roundModel.getInterest();
				totalSum += roundModel.getTake();
			}

			next = (base * playerAmount) + totalInterest;
		}

		public double getTotalBase() {
			return totalBase;
		}

		public double getTotalInterest() {
			return totalInterest;
		}

		public double getTotalSum() {
			return totalSum;
		}

	}

	public class RoundModel {
		public int round;
		public double base;
		public double interest;
		public double take;
		public double give;

		public double getTake() {
			return take;
		}

		public void setTake(double take) {
			this.take = take;
		}

		public double getGive() {
			return give;
		}

		public void setGive(double give) {
			this.give = give;
		}

		public int getRound() {
			return round;
		}

		public void setRound(int round) {
			this.round = round;
		}

		public double getBase() {
			return base;
		}

		public void setBase(double base) {
			this.base = base;
		}

		public double getInterest() {
			return interest;
		}

		public void setInterest(double interest) {
			this.interest = interest;
		}

	}

}
