package Dominio;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import ServidorApresentacao.formbeans.assiduousness.AssociarHorarioForm;
import ServidorApresentacao.formbeans.assiduousness.AssociarHorarioTipoConfirmarForm;
import ServidorApresentacao.formbeans.assiduousness.AssociarHorarioTipoForm;
import constants.assiduousness.Constants;

/**
 *
 * @author  Fernanda Quit�rio e Tania Pous�o
 */
public class Flexivel implements IStrategyHorarios {
	public Flexivel() {
	}

	public ActionErrors validateAssociarHorario(ActionForm form) {
		ActionErrors errors = new ActionErrors();

		AssociarHorarioForm formHorario = (AssociarHorarioForm)form;
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		List listaRegime = null;

		float duracaoSemanal = 0;

		int inicioExpedienteHoras = 0;
		int inicioExpedienteMinutos = 0;
		int fimExpedienteHoras = 0;
		int fimExpedienteMinutos = 0;

		long timeInicioExpediente = 0;
		long timeFimExpediente = 0;

		int inicioRefeicaoHoras = 0;
		int inicioRefeicaoMinutos = 0;
		int fimRefeicaoHoras = 0;
		int fimRefeicaoMinutos = 0;
		int intervaloMinimoRefeicaoHoras = 0;
		int intervaloMinimoRefeicaoMinutos = 0;
		int descontoObrigatorioMinutos = 0;
		int descontoObrigatorioHoras = 0;

		long timeInicioRefeicao = 0;
		long timeFimRefeicao = 0;

		int inicioHN1Horas = 0;
		int inicioHN1Minutos = 0;
		int fimHN1Horas = 0;
		int fimHN1Minutos = 0;
		int inicioHN2Horas = 0;
		int inicioHN2Minutos = 0;
		int fimHN2Horas = 0;
		int fimHN2Minutos = 0;

		long timeInicioHN1 = 0;
		long timeFimHN1 = 0;
		long timeInicioHN2 = 0;
		long timeFimHN2 = 0;

		long timeHNPeriodo = 0;

		int inicioPF1Horas = 0;
		int inicioPF1Minutos = 0;
		int fimPF1Horas = 0;
		int fimPF1Minutos = 0;
		int inicioPF2Horas = 0;
		int inicioPF2Minutos = 0;
		int fimPF2Horas = 0;
		int fimPF2Minutos = 0;

		long timeInicioPF1 = 0;
		long timeFimPF1 = 0;
		long timeInicioPF2 = 0;
		long timeFimPF2 = 0;
		long timePFPeriodo = 0;

		int diaInicio = 0;
		int mesInicio = 0;
		int anoInicio = 0;
		int diaFim = 0;
		int mesFim = 0;
		int anoFim = 0;

		try {
			if (formHorario.getDuracaoSemanal().length() < 1) {
				errors.add("DuracaoSemanal-HFlexivel", new ActionError("error.duracaoSemanal.obrigatoria"));
			} else {
				try {
					duracaoSemanal = (new Float(formHorario.getDuracaoSemanal())).floatValue();
				} catch (java.lang.NumberFormatException e) {
					errors.add("numero", new ActionError("error.numero.naoInteiro"));
				}
				if (duracaoSemanal != Constants.DURACAO_SEMANAL_FLEXIVEL) {
					errors.add("DuracaoSemanal-HFlexivel", new ActionError("error.duracaoSemanal"));
				}
			}

			if ((formHorario.getInicioExpedienteHoras().length() < 1)
				|| (formHorario.getInicioExpedienteMinutos().length() < 1)
				|| (formHorario.getFimExpedienteHoras().length() < 1)
				|| (formHorario.getFimExpedienteMinutos().length() < 1)) {
				errors.add("Expediente", new ActionError("error.Expediente.obrigatorio"));
			} else {
				try {
					inicioExpedienteHoras = (new Integer(formHorario.getInicioExpedienteHoras())).intValue();
					inicioExpedienteMinutos =
						(new Integer(formHorario.getInicioExpedienteMinutos())).intValue();
					fimExpedienteHoras = (new Integer(formHorario.getFimExpedienteHoras())).intValue();
					fimExpedienteMinutos = (new Integer(formHorario.getFimExpedienteMinutos())).intValue();
				} catch (java.lang.NumberFormatException e) {
					errors.add("numero", new ActionError("error.numero.naoInteiro"));
				}

				calendar.clear();
				if (formHorario.getDiaAnteriorExpediente().length() < 1) {
					calendar.set(1970, 0, 1, inicioExpedienteHoras, inicioExpedienteMinutos, 00);
					timeInicioExpediente = calendar.getTimeInMillis();
				} else {
					//contagem no dia anterior
					calendar.set(1969, 11, 31, inicioExpedienteHoras, inicioExpedienteMinutos, 00);
					timeInicioExpediente = calendar.getTimeInMillis();
				}

				calendar.clear();
				if (formHorario.getDiaSeguinteExpediente().length() < 1) {
					calendar.set(1970, 0, 1, fimExpedienteHoras, fimExpedienteMinutos, 00);
					timeFimExpediente = calendar.getTimeInMillis();
				} else {
					//contagem no dia seguinte
					calendar.set(1970, 0, 2, fimExpedienteHoras, fimExpedienteMinutos, 00);
					timeFimExpediente = calendar.getTimeInMillis();
				}

				if (!(timeInicioExpediente < timeFimExpediente)) {
					errors.add("Expediente", new ActionError("error.Expediente"));
				} else if (
					(timeInicioExpediente < Constants.EXPEDIENTE_MINIMO)
						|| (timeFimExpediente > Constants.EXPEDIENTE_MAXIMO)) {
					errors.add("Expediente", new ActionError("error.Expediente"));
				}
			}

			if ((formHorario.getInicioRefeicaoHoras().length() < 1)
				|| (formHorario.getInicioRefeicaoMinutos().length() < 1)
				|| (formHorario.getFimRefeicaoHoras().length() < 1)
				|| (formHorario.getFimRefeicaoMinutos().length() < 1)) {
				errors.add("Intervalo de Refeicao", new ActionError("error.refeicao.obrigatorio"));
			} else {
				try {
					inicioRefeicaoHoras = new Integer(formHorario.getInicioRefeicaoHoras()).intValue();
					inicioRefeicaoMinutos = new Integer(formHorario.getInicioRefeicaoMinutos()).intValue();
					fimRefeicaoHoras = new Integer(formHorario.getFimRefeicaoHoras()).intValue();
					fimRefeicaoMinutos = new Integer(formHorario.getFimRefeicaoMinutos()).intValue();
				} catch (java.lang.NumberFormatException e) {
					errors.add("numero", new ActionError("error.numero.naoInteiro"));
				}

				calendar.clear();
				if (formHorario.getDiaAnteriorRefeicao().length() < 1) {
					calendar.set(1970, 0, 1, inicioRefeicaoHoras, inicioRefeicaoMinutos, 00);
				} else {
					// contagem no dia anterior
					calendar.set(1969, 11, 31, inicioRefeicaoHoras, inicioRefeicaoMinutos, 00);
				}
				timeInicioRefeicao = calendar.getTimeInMillis();

				calendar.clear();
				if (formHorario.getDiaSeguinteRefeicao().length() < 1) {
					calendar.set(1970, 0, 1, fimRefeicaoHoras, fimRefeicaoMinutos, 00);
				} else {
					// contagem no dia seguinte
					calendar.set(1970, 0, 2, fimRefeicaoHoras, fimRefeicaoMinutos, 00);
				}
				timeFimRefeicao = calendar.getTimeInMillis();

				if (!(timeInicioRefeicao < timeFimRefeicao)) {
					errors.add("Refeicao", new ActionError("error.refeicao"));
				}
			}

			if ((formHorario.getIntervaloMinimoHoras().length() < 1)
				|| (formHorario.getIntervaloMinimoMinutos().length() < 1)
				|| (formHorario.getDescontoObrigatorioHoras().length() < 1)
				|| (formHorario.getDescontoObrigatorioMinutos().length() < 1)) {
				errors.add(
					"Intervalo de Refeicao",
					new ActionError("error.refeicao.intervaloEDesconto.obrigatorios"));
			} else {
				try {

					intervaloMinimoRefeicaoHoras =
						new Integer(formHorario.getIntervaloMinimoHoras()).intValue();
					intervaloMinimoRefeicaoMinutos =
						new Integer(formHorario.getIntervaloMinimoMinutos()).intValue();
					descontoObrigatorioHoras =
						new Integer(formHorario.getDescontoObrigatorioHoras()).intValue();
					descontoObrigatorioMinutos =
						new Integer(formHorario.getDescontoObrigatorioMinutos()).intValue();
				} catch (java.lang.NumberFormatException e) {
					errors.add("numero", new ActionError("error.numero.naoInteiro"));
				}
			}

			if ((formHorario.getInicioHN1Horas().length() < 1)
				|| (formHorario.getInicioHN1Minutos().length() < 1)
				|| (formHorario.getFimHN1Horas().length() < 1)
				|| (formHorario.getFimHN1Minutos().length() < 1)
				|| (formHorario.getInicioHN2Horas().length() < 1)
				|| (formHorario.getInicioHN2Minutos().length() < 1)
				|| (formHorario.getFimHN2Horas().length() < 1)
				|| (formHorario.getFimHN2Minutos().length() < 1)) {
				errors.add("HorarioNormal", new ActionError("error.HorarioNormal.obrigatorio"));
			} else {
				try {
					inicioHN1Horas = (new Integer(formHorario.getInicioHN1Horas())).intValue();
					inicioHN1Minutos = (new Integer(formHorario.getInicioHN1Minutos())).intValue();
					fimHN1Horas = (new Integer(formHorario.getFimHN1Horas())).intValue();
					fimHN1Minutos = (new Integer(formHorario.getFimHN1Minutos())).intValue();
					inicioHN2Horas = (new Integer(formHorario.getInicioHN2Horas())).intValue();
					inicioHN2Minutos = (new Integer(formHorario.getInicioHN2Minutos())).intValue();
					fimHN2Horas = (new Integer(formHorario.getFimHN2Horas())).intValue();
					fimHN2Minutos = (new Integer(formHorario.getFimHN2Minutos())).intValue();
				} catch (java.lang.NumberFormatException e) {
					errors.add("numero", new ActionError("error.numero.naoInteiro"));
				}

				calendar.clear();
				if (formHorario.getDiaAnteriorHN1().length() < 1) {
					calendar.set(1970, 0, 1, inicioHN1Horas, inicioHN1Minutos, 00);
				} else {
					// contagem no dia anterior
					calendar.set(1969, 11, 31, inicioHN1Horas, inicioHN1Minutos, 00);
				}
				timeInicioHN1 = calendar.getTimeInMillis();

				calendar.clear();
				if (formHorario.getDiaSeguinteHN1().length() < 1) {
					calendar.set(1970, 0, 1, fimHN1Horas, fimHN1Minutos, 00);
				} else {
					// contagem no dia seguinte
					calendar.set(1970, 0, 2, fimHN1Horas, fimHN1Minutos, 00);
				}
				timeFimHN1 = calendar.getTimeInMillis();

				calendar.clear();
				if (formHorario.getDiaAnteriorHN2().length() < 1) {
					calendar.set(1970, 0, 1, inicioHN2Horas, inicioHN2Minutos, 00);
				} else {
					// contagem no dia anterior
					calendar.set(1969, 11, 31, inicioHN2Horas, inicioHN2Minutos, 00);
				}
				timeInicioHN2 = calendar.getTimeInMillis();

				calendar.clear();
				if (formHorario.getDiaSeguinteHN2().length() < 1) {
					calendar.set(1970, 0, 1, fimHN2Horas, fimHN2Minutos, 00);
				} else {
					// contagem no dia seguinte
					calendar.set(1970, 0, 2, fimHN2Horas, fimHN2Minutos, 00);
				}
				timeFimHN2 = calendar.getTimeInMillis();

				if (timeInicioExpediente != 0) {
					if (!(timeInicioExpediente < timeInicioHN1)) {
						errors.add(
							"ExpedienteHN",
							new ActionError("error.ExpedienteHorarioNormal.inconsistentes"));
					}
				}

				if (timeFimExpediente != 0) {
					if (!(timeFimExpediente > timeFimHN2)) {
						errors.add(
							"ExpedienteHN",
							new ActionError("error.ExpedienteHorarioNormal.inconsistentes"));
					}
				}

				if (!((timeInicioHN1 < timeFimHN1)
					&& (timeFimHN1 < timeInicioHN2)
					&& (timeInicioHN2 < timeFimHN2))) {
					errors.add("HN", new ActionError("error.HorarioNormal"));
				} else {
					// 2 periodos do horario normal que somam 7 horas diarias no total					
					timeHNPeriodo =
						((new Float(Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
							.intValue()
							* 3600
							* 1000)
							+ new Float(
								(new Float(Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
									.floatValue()
									- new Float(
										Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
										.intValue())
									* 3600
									* 1000)
								.longValue());
					if (((timeFimHN1 - timeInicioHN1) + (timeFimHN2 - timeInicioHN2)) != timeHNPeriodo) {
						errors.add("HNPeriodo", new ActionError("error.HorarioNormal.duracao"));
					}

					if (((timeFimHN1 - timeInicioHN1) > Constants.MAX_TRABALHO_FLEXIVEL)
						|| ((timeFimHN2 - timeInicioHN2) > Constants.MAX_TRABALHO_FLEXIVEL)) {
						errors.add("HNMax", new ActionError("error.HorarioNormal.trabalhoMax"));
					}

				}
			}

			if (formHorario.getRegime().length < 0) {
				errors.add("regime", new ActionError("error.regime.obrigatorio"));
			} else {
				listaRegime = Arrays.asList(formHorario.getRegime());

				if (listaRegime.contains(Constants.IPF)) { //Regime:new String("isencaoPeriodoFixo")
					if ((formHorario.getInicioPF1Horas().length() > 0)
						|| (formHorario.getInicioPF1Minutos().length() > 0)
						|| (formHorario.getFimPF1Horas().length() > 0)
						|| (formHorario.getFimPF1Minutos().length() > 0)
						|| (formHorario.getInicioPF2Horas().length() > 0)
						|| (formHorario.getInicioPF2Minutos().length() > 0)
						|| (formHorario.getFimPF2Horas().length() > 0)
						|| (formHorario.getFimPF2Minutos().length() > 0)) {
						errors.add("periodoFixo", new ActionError("error.PeriodoFixo.naoPermitido"));
					}

					/*  Para hor�rios com IPF n�o existe intervalo obrigatorio para almo�o
					 
					// Marca��es de ponto para a hora do almo�o 
					calendar.clear();
					calendar.set(1970, 0, 1, fimHN1Horas, fimHN1Minutos, 00);
					timeFimHN1 = calendar.getTimeInMillis();
					
					calendar.clear();
					calendar.set(1970, 0, 1, inicioHN2Horas, inicioHN2Minutos, 00);
					timeInicioHN2 = calendar.getTimeInMillis();
					
					if ((timeFimHN1 < Constants.INICIO_REFEICAO_IPF)
						|| (timeInicioHN2 > Constants.FIM_REFEICAO_IPF)) {
						errors.add("almoco", new ActionError("error.PeriodoAlmoco.invalido"));
					}
					*/
				} else {

					if ((formHorario.getInicioPF1Horas().length() < 1)
						|| (formHorario.getInicioPF1Minutos().length() < 1)
						|| (formHorario.getFimPF1Horas().length() < 1)
						|| (formHorario.getFimPF1Minutos().length() < 1)
						|| (formHorario.getInicioPF2Horas().length() < 1)
						|| (formHorario.getInicioPF2Minutos().length() < 1)
						|| (formHorario.getFimPF2Horas().length() < 1)
						|| (formHorario.getFimPF2Minutos().length() < 1)) {
						errors.add("periodoFixo", new ActionError("error.PeriodoFixo.obrigatorio"));
					} else {
						try {
							inicioPF1Horas = (new Integer(formHorario.getInicioPF1Horas())).intValue();
							inicioPF1Minutos = (new Integer(formHorario.getInicioPF1Minutos())).intValue();
							fimPF1Horas = (new Integer(formHorario.getFimPF1Horas())).intValue();
							fimPF1Minutos = (new Integer(formHorario.getFimPF1Minutos())).intValue();
							inicioPF2Horas = (new Integer(formHorario.getInicioPF2Horas())).intValue();
							inicioPF2Minutos = (new Integer(formHorario.getInicioPF2Minutos())).intValue();
							fimPF2Horas = (new Integer(formHorario.getFimPF2Horas())).intValue();
							fimPF2Minutos = (new Integer(formHorario.getFimPF2Minutos())).intValue();
						} catch (java.lang.NumberFormatException e) {
							errors.add("numero", new ActionError("error.numero.naoInteiro"));
						}

						calendar.clear();
						if (formHorario.getDiaAnteriorPF1().length() < 1) {
							calendar.set(1970, 0, 1, inicioPF1Horas, inicioPF1Minutos, 00);
						} else {
							// contagem no dia anterior
							calendar.set(1969, 11, 31, inicioPF1Horas, inicioPF1Minutos, 00);
						}
						timeInicioPF1 = calendar.getTimeInMillis();

						calendar.clear();
						if (formHorario.getDiaSeguintePF1().length() < 1) {
							calendar.set(1970, 0, 1, fimPF1Horas, fimPF1Minutos, 00);
						} else {
							// contagem no dia seguinte
							calendar.set(1970, 0, 2, fimPF1Horas, fimPF1Minutos, 00);
						}
						timeFimPF1 = calendar.getTimeInMillis();

						calendar.clear();
						if (formHorario.getDiaAnteriorPF2().length() < 1) {
							calendar.set(1970, 0, 1, inicioPF2Horas, inicioPF2Minutos, 00);
						} else {
							// contagem no dia anterior
							calendar.set(1969, 11, 31, inicioPF2Horas, inicioPF2Minutos, 00);
						}
						timeInicioPF2 = calendar.getTimeInMillis();

						calendar.clear();
						if (formHorario.getDiaSeguintePF2().length() < 1) {
							calendar.set(1970, 0, 1, fimPF2Horas, fimPF2Minutos, 00);
						} else {
							// contagem no dia seguinte
							calendar.set(1970, 0, 2, fimPF2Horas, fimPF2Minutos, 00);
						}
						timeFimPF2 = calendar.getTimeInMillis();

						if (!((timeInicioPF1 < timeFimPF1)
							&& (timeFimPF1 < timeInicioPF2)
							&& (timeInicioPF2 < timeFimPF2))) {
							errors.add("PF", new ActionError("error.PeriodoFixo"));
						} else {
							// 2 plataformas fixas nao inferiores a 4 horas no total
							timePFPeriodo = Constants.PLATAFORMAS_FIXAS_FLEXIVEL;

							if (!(((timeFimPF1 - timeInicioPF1) + (timeFimPF2 - timeInicioPF2))
								== timePFPeriodo)) {
								errors.add("PFPeriodo", new ActionError("error.PeriodoFixo.duracao"));
							}

							if (!((timeInicioHN1 <= timeInicioPF1)
								&& (timeFimPF1 <= timeFimHN1)
								&& (timeInicioHN2 <= timeInicioPF2)
								&& (timeFimPF2 <= timeFimHN2))) {
								errors.add(
									"HNPF",
									new ActionError("error.HorarioNormalPeriodoFixo.inconsistentes"));
							} else if (
								!((timeFimPF1 <= timeInicioRefeicao) && (timeFimRefeicao <= timeInicioPF2))) {
								errors.add("Refeicao", new ActionError("error.refeicao"));
							}
						}
					}
				}
			}

			if (!formHorario.isExcepcaoHorario()) {

				// data de validade 
				if ((formHorario.getDiaInicio() != null)
					&& (formHorario.getMesInicio() != null)
					&& (formHorario.getAnoInicio() != null)) {
					if ((formHorario.getDiaInicio().length() < 1)
						|| (formHorario.getMesInicio().length() < 1)
						|| (formHorario.getAnoInicio().length() < 1)) {
						errors.add("dates", new ActionError("error.dataValidade.obrigatoria"));
					} else {
						try {
							diaInicio = (new Integer(formHorario.getDiaInicio())).intValue();
							mesInicio = (new Integer(formHorario.getMesInicio())).intValue();
							anoInicio = (new Integer(formHorario.getAnoInicio())).intValue();
						} catch (java.lang.NumberFormatException e) {
							errors.add("numero", new ActionError("error.numero.naoInteiro"));
						}
						calendar.clear();
						calendar.set(anoInicio, mesInicio - 1, diaInicio, 00, 00, 00);
						java.util.Date dataInicio = calendar.getTime();

						if ((formHorario.getDiaFim() != null)
							&& (formHorario.getMesFim() != null)
							&& (formHorario.getAnoFim() != null)) {
							if ((formHorario.getDiaFim().length() > 0)
								&& (formHorario.getMesFim().length() > 0)
								&& (formHorario.getAnoFim().length() > 0)) {
								try {
									diaFim = (new Integer(formHorario.getDiaFim())).intValue();
									mesFim = (new Integer(formHorario.getMesFim())).intValue();
									anoFim = (new Integer(formHorario.getAnoFim())).intValue();
								} catch (java.lang.NumberFormatException e) {
									errors.add("numero", new ActionError("error.numero.naoInteiro"));
								}

								calendar.clear();
								calendar.set(anoFim, mesFim - 1, diaFim, 00, 00, 00);
								java.util.Date dataFim = calendar.getTime();

								if (!(dataInicio.getTime() < dataFim.getTime())) {
									errors.add("datas", new ActionError("error.dataValidade.incorrecta"));
								}
							}
						}
					}
				}
			} else {
				if ((formHorario.getDiaInicio() != null)
					&& (formHorario.getMesInicio() != null)
					&& (formHorario.getAnoInicio() != null)
					&& (formHorario.getDiaFim() != null)
					&& (formHorario.getMesFim() != null)
					&& (formHorario.getAnoFim() != null)) {
					if ((formHorario.getDiaInicio().length() < 1)
						|| (formHorario.getMesInicio().length() < 1)
						|| (formHorario.getAnoInicio().length() < 1)
						|| (formHorario.getDiaFim().length() < 1)
						|| (formHorario.getMesFim().length() < 1)
						|| (formHorario.getAnoFim().length() < 1)) {
						errors.add("dates", new ActionError("error.dataValidade.obrigatoria"));
					} else {
						try {
							diaInicio = (new Integer(formHorario.getDiaInicio())).intValue();
							mesInicio = (new Integer(formHorario.getMesInicio())).intValue();
							anoInicio = (new Integer(formHorario.getAnoInicio())).intValue();
							diaFim = (new Integer(formHorario.getDiaFim())).intValue();
							mesFim = (new Integer(formHorario.getMesFim())).intValue();
							anoFim = (new Integer(formHorario.getAnoFim())).intValue();
						} catch (java.lang.NumberFormatException e) {
							errors.add("numero", new ActionError("error.numero.naoInteiro"));
						}

						Calendar calendarInicio = Calendar.getInstance();
						Calendar calendarFim = Calendar.getInstance();

						calendarInicio.clear();
						calendarInicio.set(anoInicio, mesInicio - 1, diaInicio, 00, 00, 00);
						java.util.Date dataInicio = calendarInicio.getTime();

						calendarFim.clear();
						calendarFim.set(anoFim, mesFim - 1, diaFim, 00, 00, 00);
						java.util.Date dataFim = calendarFim.getTime();

						if (!(dataInicio.getTime() <= dataFim.getTime())) {
							errors.add("datas", new ActionError("error.dataValidade.incorrecta"));
						}
					}
				}
			}
		} catch (java.lang.IllegalArgumentException e) {
			errors.add("horasData", new ActionError("error.data.horas"));
		}
		return errors;
	} /* validateAssociarHorario */

	public ActionErrors validateAssociarHorarioTipo(
		ActionForm form,
		HorarioTipo horarioTipo,
		ArrayList listaRegimes) {
		ActionErrors errors = new ActionErrors();

		AssociarHorarioTipoForm formHorario = (AssociarHorarioTipoForm)form;
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		int diaInicio = 0;
		int mesInicio = 0;
		int anoInicio = 0;
		int diaFim = 0;
		int mesFim = 0;
		int anoFim = 0;

		try {
			// data de validade 
			if (!formHorario.isExcepcaoHorario()) {
				if ((formHorario.getDiaInicio() != null)
					&& (formHorario.getMesInicio() != null)
					&& (formHorario.getAnoInicio() != null)) {
					if ((formHorario.getDiaInicio().length() < 1)
						|| (formHorario.getMesInicio().length() < 1)
						|| (formHorario.getAnoInicio().length() < 1)) {
						errors.add("dates", new ActionError("error.dataValidade.obrigatoria"));
					} else {
						try {
							diaInicio = (new Integer(formHorario.getDiaInicio())).intValue();
							mesInicio = (new Integer(formHorario.getMesInicio())).intValue();
							anoInicio = (new Integer(formHorario.getAnoInicio())).intValue();
						} catch (java.lang.NumberFormatException e) {
							errors.add("numero", new ActionError("error.numero.naoInteiro"));
						}
						calendar.clear();
						calendar.set(anoInicio, mesInicio - 1, diaInicio, 00, 00, 00);
						java.util.Date dataInicio = calendar.getTime();

						if ((formHorario.getDiaFim() != null)
							&& (formHorario.getMesFim() != null)
							&& (formHorario.getAnoFim() != null)) {
							if ((formHorario.getDiaFim().length() > 0)
								&& (formHorario.getMesFim().length() > 0)
								&& (formHorario.getAnoFim().length() > 0)) {
								try {
									diaFim = (new Integer(formHorario.getDiaFim())).intValue();
									mesFim = (new Integer(formHorario.getMesFim())).intValue();
									anoFim = (new Integer(formHorario.getAnoFim())).intValue();
								} catch (java.lang.NumberFormatException e) {
									errors.add("numero", new ActionError("error.numero.naoInteiro"));
								}

								calendar.clear();
								calendar.set(anoFim, mesFim - 1, diaFim, 00, 00, 00);
								java.util.Date dataFim = calendar.getTime();

								if (!(dataInicio.getTime() < dataFim.getTime())) {
									errors.add("datas", new ActionError("error.dataValidade.incorrecta"));
								}
							}
						}
					}
				}
			} else {
				if ((formHorario.getDiaInicio() != null)
					&& (formHorario.getMesInicio() != null)
					&& (formHorario.getAnoInicio() != null)
					&& (formHorario.getDiaFim() != null)
					&& (formHorario.getMesFim() != null)
					&& (formHorario.getAnoFim() != null)) {
					if ((formHorario.getDiaInicio().length() < 1)
						|| (formHorario.getMesInicio().length() < 1)
						|| (formHorario.getAnoInicio().length() < 1)
						|| (formHorario.getDiaFim().length() < 1)
						|| (formHorario.getMesFim().length() < 1)
						|| (formHorario.getAnoFim().length() < 1)) {
						errors.add("dates", new ActionError("error.dataValidade.obrigatoria"));
					} else {
						try {
							diaInicio = (new Integer(formHorario.getDiaInicio())).intValue();
							mesInicio = (new Integer(formHorario.getMesInicio())).intValue();
							anoInicio = (new Integer(formHorario.getAnoInicio())).intValue();
							diaFim = (new Integer(formHorario.getDiaFim())).intValue();
							mesFim = (new Integer(formHorario.getMesFim())).intValue();
							anoFim = (new Integer(formHorario.getAnoFim())).intValue();
						} catch (java.lang.NumberFormatException e) {
							errors.add("numero", new ActionError("error.numero.naoInteiro"));
						}

						Calendar calendarInicio = Calendar.getInstance();
						Calendar calendarFim = Calendar.getInstance();

						calendarInicio.clear();
						calendarInicio.set(anoInicio, mesInicio - 1, diaInicio, 00, 00, 00);
						java.util.Date dataInicio = calendarInicio.getTime();

						calendarFim.clear();
						calendarFim.set(anoFim, mesFim - 1, diaFim, 00, 00, 00);
						java.util.Date dataFim = calendarFim.getTime();

						if (!(dataInicio.getTime() <= dataFim.getTime())) {
							errors.add("datas", new ActionError("error.dataValidade.incorrecta"));
						}
					}
				}
			}
		} catch (java.lang.IllegalArgumentException e) {
			errors.add("horasData", new ActionError("error.dataValidade.incorrecta"));
		}
		return errors;
	} /* validateAssociarHorarioTipo */

	public void setHorario(Horario horario, ActionForm form) {
		AssociarHorarioForm formHorario = (AssociarHorarioForm)form;
		Calendar calendar = Calendar.getInstance();
		List listaRegime = null;

		horario.setModalidade(formHorario.getModalidade());
		horario.setDuracaoSemanal((new Float(formHorario.getDuracaoSemanal())).floatValue());

		if ((formHorario.getNumDias() == null)
			|| (formHorario.getPosicao() == null)
			|| (formHorario.getNumDias().length() < 1)
			|| (formHorario.getPosicao().length() < 1)) {
			horario.setNumDias(Constants.NUMDIAS_ROTACAO);
			horario.setPosicao(Constants.INICIO_ROTACAO);
		} else {
			horario.setNumDias(Integer.valueOf(formHorario.getNumDias()).intValue());
			horario.setPosicao(Integer.valueOf(formHorario.getPosicao()).intValue());
		}

		// Expediente 
		calendar.clear();
		if (formHorario.getDiaAnteriorExpediente().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getInicioExpedienteHoras())).intValue(),
				(new Integer(formHorario.getInicioExpedienteMinutos())).intValue(),
				00);
			horario.setInicioExpediente(new Timestamp(calendar.getTimeInMillis()));
		} else {
			//contagem no dia anterior
			calendar.set(
				1969,
				11,
				31,
				(new Integer(formHorario.getInicioExpedienteHoras())).intValue(),
				(new Integer(formHorario.getInicioExpedienteMinutos())).intValue(),
				00);
			horario.setInicioExpediente(new Timestamp(calendar.getTimeInMillis()));
		}

		calendar.clear();
		if (formHorario.getDiaSeguinteExpediente().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getFimExpedienteHoras())).intValue(),
				(new Integer(formHorario.getFimExpedienteMinutos())).intValue(),
				00);
			horario.setFimExpediente(new Timestamp(calendar.getTimeInMillis()));
		} else {
			//contagem no dia seguinte
			calendar.set(
				1970,
				0,
				2,
				(new Integer(formHorario.getFimExpedienteHoras())).intValue(),
				(new Integer(formHorario.getFimExpedienteMinutos())).intValue(),
				00);
			horario.setFimExpediente(new Timestamp(calendar.getTimeInMillis()));
		}

		calendar.clear();
		calendar.set(
			1970,
			0,
			1,
			new Integer(formHorario.getIntervaloMinimoHoras()).intValue(),
			new Integer(formHorario.getIntervaloMinimoMinutos()).intValue(),
			00);
		horario.setIntervaloMinimoRefeicao(new Time(calendar.getTimeInMillis()));

		calendar.clear();
		calendar.set(
			1970,
			0,
			1,
			new Integer(formHorario.getDescontoObrigatorioHoras()).intValue(),
			new Integer(formHorario.getDescontoObrigatorioMinutos()).intValue(),
			00);
		horario.setDescontoObrigatorioRefeicao(new Time(calendar.getTimeInMillis()));

		//	Intervalo de Refeicao
		calendar.clear();
		if (formHorario.getDiaAnteriorRefeicao().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				new Integer(formHorario.getInicioRefeicaoHoras()).intValue(),
				new Integer(formHorario.getInicioRefeicaoMinutos()).intValue(),
				00);
		} else {
			//contagem no dia anterior
			calendar.set(
				1969,
				11,
				31,
				(new Integer(formHorario.getInicioRefeicaoHoras())).intValue(),
				(new Integer(formHorario.getInicioRefeicaoMinutos())).intValue(),
				00);
		}
		horario.setInicioRefeicao(new Timestamp(calendar.getTimeInMillis()));

		calendar.clear();
		if (formHorario.getDiaSeguinteRefeicao().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				new Integer(formHorario.getFimRefeicaoHoras()).intValue(),
				new Integer(formHorario.getFimRefeicaoMinutos()).intValue(),
				00);
		} else {
			//contagem no dia seguinte
			calendar.set(
				1970,
				0,
				2,
				(new Integer(formHorario.getFimRefeicaoHoras())).intValue(),
				(new Integer(formHorario.getFimRefeicaoMinutos())).intValue(),
				00);
		}
		horario.setFimRefeicao(new Timestamp(calendar.getTimeInMillis()));

		// Periodos Normais 
		calendar.clear();
		if (formHorario.getDiaAnteriorHN1().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getInicioHN1Horas())).intValue(),
				(new Integer(formHorario.getInicioHN1Minutos())).intValue(),
				00);
		} else {
			//contagem no dia anterior
			calendar.set(
				1969,
				11,
				31,
				(new Integer(formHorario.getInicioHN1Horas())).intValue(),
				(new Integer(formHorario.getInicioHN1Minutos())).intValue(),
				00);
		}
		horario.setInicioHN1(new Timestamp(calendar.getTimeInMillis()));

		calendar.clear();
		if (formHorario.getDiaSeguinteHN1().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getFimHN1Horas())).intValue(),
				(new Integer(formHorario.getFimHN1Minutos())).intValue(),
				00);
		} else {
			//contagem no dia seguinte
			calendar.set(
				1970,
				0,
				2,
				(new Integer(formHorario.getFimHN1Horas())).intValue(),
				(new Integer(formHorario.getFimHN1Minutos())).intValue(),
				00);
		}
		horario.setFimHN1(new Timestamp(calendar.getTimeInMillis()));

		if (formHorario.getDiaAnteriorHN2().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getInicioHN2Horas())).intValue(),
				(new Integer(formHorario.getInicioHN2Minutos())).intValue(),
				00);
		} else {
			//contagem no dia anterior
			calendar.set(
				1969,
				11,
				31,
				(new Integer(formHorario.getInicioHN2Horas())).intValue(),
				(new Integer(formHorario.getInicioHN2Minutos())).intValue(),
				00);
		}
		horario.setInicioHN2(new Timestamp(calendar.getTimeInMillis()));

		calendar.clear();
		if (formHorario.getDiaSeguinteHN2().length() < 1) {
			calendar.set(
				1970,
				0,
				1,
				(new Integer(formHorario.getFimHN2Horas())).intValue(),
				(new Integer(formHorario.getFimHN2Minutos())).intValue(),
				00);
		} else {
			//contagem no dia seguinte
			calendar.set(
				1970,
				0,
				2,
				(new Integer(formHorario.getFimHN2Horas())).intValue(),
				(new Integer(formHorario.getFimHN2Minutos())).intValue(),
				00);
		}
		horario.setFimHN2(new Timestamp(calendar.getTimeInMillis()));

		// Data de Validade
		if ((formHorario.getDiaInicio() != null)
			&& (formHorario.getMesInicio() != null)
			&& (formHorario.getAnoInicio() != null)) {
			if ((formHorario.getDiaInicio().length() > 0)
				&& (formHorario.getMesInicio().length() > 0)
				&& (formHorario.getAnoInicio().length() > 0)) {
				calendar.clear();
				calendar.set(
					(new Integer(formHorario.getAnoInicio())).intValue(),
					(new Integer(formHorario.getMesInicio())).intValue() - 1,
					(new Integer(formHorario.getDiaInicio())).intValue(),
					00,
					00,
					00);
				horario.setDataInicio(calendar.getTime());
			}
		}
		if ((formHorario.getDiaFim() != null)
			&& (formHorario.getMesFim() != null)
			&& (formHorario.getAnoFim() != null)) {
			if ((formHorario.getDiaFim().length() > 0)
				&& (formHorario.getMesFim().length() > 0)
				&& (formHorario.getAnoFim().length() > 0)) {
				calendar.clear();
				calendar.set(
					(new Integer(formHorario.getAnoFim())).intValue(),
					(new Integer(formHorario.getMesFim())).intValue() - 1,
					(new Integer(formHorario.getDiaFim())).intValue(),
					00,
					00,
					00);
				horario.setDataFim(calendar.getTime());
			}
		}

		// Periodos Fixos 
		listaRegime = Arrays.asList(formHorario.getRegime());
		if (!listaRegime.contains(Constants.IPF)) { //Regimes: new String("isencaoPeriodoFixo")
			if (formHorario.getDiaAnteriorPF1().length() < 1) {
				calendar.set(
					1970,
					0,
					1,
					(new Integer(formHorario.getInicioPF1Horas())).intValue(),
					(new Integer(formHorario.getInicioPF1Minutos())).intValue(),
					00);
			} else {
				//contagem no dia anterior
				calendar.set(
					1969,
					11,
					31,
					(new Integer(formHorario.getInicioPF1Horas())).intValue(),
					(new Integer(formHorario.getInicioPF1Minutos())).intValue(),
					00);
			}
			horario.setInicioPF1(new Timestamp(calendar.getTimeInMillis()));

			calendar.clear();
			if (formHorario.getDiaSeguintePF1().length() < 1) {
				calendar.set(
					1970,
					0,
					1,
					(new Integer(formHorario.getFimPF1Horas())).intValue(),
					(new Integer(formHorario.getFimPF1Minutos())).intValue(),
					00);
			} else {
				//contagem no dia seguinte
				calendar.set(
					1970,
					0,
					2,
					(new Integer(formHorario.getFimPF1Horas())).intValue(),
					(new Integer(formHorario.getFimPF1Minutos())).intValue(),
					00);
			}
			horario.setFimPF1(new Timestamp(calendar.getTimeInMillis()));

			if (formHorario.getDiaAnteriorPF2().length() < 1) {
				calendar.set(
					1970,
					0,
					1,
					(new Integer(formHorario.getInicioPF2Horas())).intValue(),
					(new Integer(formHorario.getInicioPF2Minutos())).intValue(),
					00);
			} else {
				//contagem no dia anterior
				calendar.set(
					1969,
					11,
					31,
					(new Integer(formHorario.getInicioPF2Horas())).intValue(),
					(new Integer(formHorario.getInicioPF2Minutos())).intValue(),
					00);
			}
			horario.setInicioPF2(new Timestamp(calendar.getTimeInMillis()));

			calendar.clear();
			if (formHorario.getDiaSeguintePF2().length() < 1) {
				calendar.set(
					1970,
					0,
					1,
					(new Integer(formHorario.getFimPF2Horas())).intValue(),
					(new Integer(formHorario.getFimPF2Minutos())).intValue(),
					00);
			} else {
				//contagem no dia seguinte
				calendar.set(
					1970,
					0,
					2,
					(new Integer(formHorario.getFimPF2Horas())).intValue(),
					(new Integer(formHorario.getFimPF2Minutos())).intValue(),
					00);
			}
			horario.setFimPF2(new Timestamp(calendar.getTimeInMillis()));
		}
	} /* setHorario */

	public void setHorarioTipoRotativo(Horario horario, ActionForm form) {
		AssociarHorarioTipoForm formHorario = (AssociarHorarioTipoForm)form;

		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		horario.setChaveHorarioTipo(Constants.CHAVE_HTIPO); // para revelar que � um hor�rio tipo

		horario.setNumDias(new Integer(formHorario.getNumDias()).intValue());
		horario.setPosicao(new Integer(formHorario.getPosicao()).intValue());

		horario.setSigla(formHorario.getSigla());
	} /* setHorarioRotativo */

	public void setDatasHorario(Horario horario, ActionForm form) {
		AssociarHorarioTipoForm formHorario = (AssociarHorarioTipoForm)form;

		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		horario.setNumDias(Constants.NUMDIAS_ROTACAO);
		horario.setPosicao(Constants.INICIO_ROTACAO);

		calendar.clear();
		calendar.set(
			(new Integer(formHorario.getAnoInicio())).intValue(),
			(new Integer(formHorario.getMesInicio())).intValue() - 1,
			(new Integer(formHorario.getDiaInicio())).intValue(),
			00,
			00,
			00);
		horario.setDataInicio(calendar.getTime());

		if ((formHorario.getDiaFim().length() > 0)
			&& (formHorario.getMesFim().length() > 0)
			&& (formHorario.getAnoFim().length() > 0)) {
			calendar.clear();
			calendar.set(
				(new Integer(formHorario.getAnoFim())).intValue(),
				(new Integer(formHorario.getMesFim())).intValue() - 1,
				(new Integer(formHorario.getDiaFim())).intValue(),
				00,
				00,
				00);
			horario.setDataFim(calendar.getTime());
		}
	} /* setDatasHorario */

	public void setFormAssociarHorarioConfirmar(
		Locale locale,
		ActionForm form,
		Pessoa pessoa,
		Funcionario funcionario,
		Horario horario,
		ArrayList listaRegime,
		boolean isExcepcaoHorario) {
		AssociarHorarioForm formHorario = (AssociarHorarioForm)form;

		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		formHorario.setExcepcaoHorario(isExcepcaoHorario);

		formHorario.setNome(pessoa.getNome());
		formHorario.setNumMecanografico((new Integer(funcionario.getNumeroMecanografico())).toString());

		formHorario.setDuracaoSemanal(
			(new Integer((new Float(horario.getDuracaoSemanal())).intValue())).toString());
		formHorario.setModalidade(horario.getModalidade());

		formHorario.setListaRegimes(listaRegime);

		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioExpediente()).getTime());
		formHorario.setInicioExpedienteHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioExpedienteMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioExpedienteMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorExpediente(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimExpediente()).getTime());
		formHorario.setFimExpedienteHoras((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimExpedienteMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimExpedienteMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteExpediente(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getIntervaloMinimoRefeicao()).getTime());
		formHorario.setIntervaloMinimoHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setIntervaloMinimoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setIntervaloMinimoMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		calendar.clear();
		calendar.setTimeInMillis((horario.getDescontoObrigatorioRefeicao()).getTime());
		formHorario.setDescontoObrigatorioHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setDescontoObrigatorioMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setDescontoObrigatorioMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		//	Intervalo de Refei��o
		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioRefeicao()).getTime());
		formHorario.setInicioRefeicaoHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioRefeicaoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioRefeicaoMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorRefeicao(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimRefeicao()).getTime());
		formHorario.setFimRefeicaoHoras((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimRefeicaoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimRefeicaoMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteRefeicao(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioHN1()).getTime());
		formHorario.setInicioHN1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioHN1Minutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioHN1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorHN1(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimHN1()).getTime());
		formHorario.setFimHN1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimHN1Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimHN1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteHN1(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioHN2()).getTime());
		formHorario.setInicioHN2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioHN2Minutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioHN2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorHN2(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimHN2()).getTime());
		formHorario.setFimHN2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimHN2Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimHN2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteHN2(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTime(horario.getDataInicio());
		formHorario.setDiaInicio((new Integer(calendar.get(Calendar.DAY_OF_MONTH))).toString());
		formHorario.setMesInicio((new Integer(calendar.get(Calendar.MONTH) + 1)).toString());
		formHorario.setAnoInicio((new Integer(calendar.get(Calendar.YEAR))).toString());

		if (horario.getDataFim() != null) {
			calendar.clear();
			calendar.setTime(horario.getDataFim());
			formHorario.setDiaFim((new Integer(calendar.get(Calendar.DAY_OF_MONTH))).toString());
			formHorario.setMesFim((new Integer(calendar.get(Calendar.MONTH) + 1)).toString());
			formHorario.setAnoFim((new Integer(calendar.get(Calendar.YEAR))).toString());
		} else {
			formHorario.setDiaFim("--");
			formHorario.setMesFim("--");
			formHorario.setAnoFim("----");
		}

		if (!listaRegime.contains(Constants.IPF)) { //Regime: new String("isencaoPeriodoFixo")
			calendar.clear();
			calendar.setTimeInMillis((horario.getInicioPF1()).getTime());
			formHorario.setInicioPF1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setInicioPF1Minutos(
					"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setInicioPF1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Anterior
				formHorario.setDiaAnteriorPF1(Constants.DIA_ANTERIOR);
			}

			calendar.clear();
			calendar.setTimeInMillis((horario.getFimPF1()).getTime());
			formHorario.setFimPF1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setFimPF1Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setFimPF1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Seguinte
				formHorario.setDiaSeguintePF1(Constants.DIA_SEGUINTE);
			}

			calendar.clear();
			calendar.setTimeInMillis((horario.getInicioPF2()).getTime());
			formHorario.setInicioPF2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setInicioPF2Minutos(
					"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setInicioPF2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Anterior
				formHorario.setDiaAnteriorPF2(Constants.DIA_ANTERIOR);
			}

			calendar.clear();
			calendar.setTimeInMillis((horario.getFimPF2()).getTime());
			formHorario.setFimPF2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setFimPF2Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setFimPF2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Seguinte
				formHorario.setDiaSeguintePF2(Constants.DIA_SEGUINTE);
			}
		} else {
			formHorario.setInicioPF1Horas("--");
			formHorario.setInicioPF1Minutos("--");

			formHorario.setFimPF1Horas("--");
			formHorario.setFimPF1Minutos("--");

			formHorario.setInicioPF2Horas("--");
			formHorario.setInicioPF2Minutos("--");

			formHorario.setFimPF2Horas("--");
			formHorario.setFimPF2Minutos("--");
		}
	} /* setFormAssociarHorarioConfirmar */

	public void setFormAssociarHorarioTipoConfirmar(
		Locale locale,
		ActionForm form,
		Pessoa pessoa,
		Funcionario funcionario,
		HorarioTipo horarioTipo,
		Horario horario,
		ArrayList listaRegime,
		boolean isExcepcaoHorario) {
		AssociarHorarioTipoConfirmarForm formHorario = (AssociarHorarioTipoConfirmarForm)form;

		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);

		formHorario.setExcepcaoHorario(isExcepcaoHorario);

		formHorario.setNome(pessoa.getNome());
		formHorario.setNumMecanografico((new Integer(funcionario.getNumeroMecanografico())).toString());

		formHorario.setDuracaoSemanal(
			(new Integer((new Float(horarioTipo.getDuracaoSemanal())).intValue())).toString());
		formHorario.setModalidade(horarioTipo.getModalidade());
		formHorario.setSigla(horarioTipo.getSigla());

		formHorario.setListaRegime(listaRegime);

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioExpediente()).getTime());
		formHorario.setInicioExpedienteHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioExpedienteMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioExpedienteMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorExpediente(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimExpediente()).getTime());
		formHorario.setFimExpedienteHoras((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimExpedienteMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimExpedienteMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteExpediente(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getIntervaloMinimoRefeicao()).getTime());
		formHorario.setIntervaloMinimoHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setIntervaloMinimoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setIntervaloMinimoMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getDescontoObrigatorioRefeicao()).getTime());
		formHorario.setDescontoObrigatorioHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setDescontoObrigatorioMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setDescontoObrigatorioMinutos(
				(new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		//			Intervalo de Refei��o
		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioRefeicao()).getTime());
		formHorario.setInicioRefeicaoHoras(
			(new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioRefeicaoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioRefeicaoMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorRefeicao(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimRefeicao()).getTime());
		formHorario.setFimRefeicaoHoras((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimRefeicaoMinutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimRefeicaoMinutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteRefeicao(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioHN1()).getTime());
		formHorario.setInicioHN1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioHN1Minutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioHN1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorHN1(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimHN1()).getTime());
		formHorario.setFimHN1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimHN1Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimHN1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteHN1(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioHN2()).getTime());
		formHorario.setInicioHN2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setInicioHN2Minutos(
				"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setInicioHN2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Anterior
			formHorario.setDiaAnteriorHN2(Constants.DIA_ANTERIOR);
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimHN2()).getTime());
		formHorario.setFimHN2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			formHorario.setFimHN2Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			formHorario.setFimHN2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
			//contagem no Dia Seguinte
			formHorario.setDiaSeguinteHN2(Constants.DIA_SEGUINTE);
		}

		calendar.clear();
		calendar.setTime(horario.getDataInicio());
		formHorario.setDiaInicio((new Integer(calendar.get(Calendar.DAY_OF_MONTH))).toString());
		formHorario.setMesInicio((new Integer(calendar.get(Calendar.MONTH) + 1)).toString());
		formHorario.setAnoInicio((new Integer(calendar.get(Calendar.YEAR))).toString());

		if (horario.getDataFim() != null) {
			calendar.clear();
			calendar.setTime(horario.getDataFim());
			formHorario.setDiaFim((new Integer(calendar.get(Calendar.DAY_OF_MONTH))).toString());
			formHorario.setMesFim((new Integer(calendar.get(Calendar.MONTH) + 1)).toString());
			formHorario.setAnoFim((new Integer(calendar.get(Calendar.YEAR))).toString());
		} else {
			formHorario.setDiaFim("--");
			formHorario.setMesFim("--");
			formHorario.setAnoFim("----");
		}

		if (!listaRegime.contains(Constants.IPF)) { //Regime: new String("isencaoPeriodoFixo")
			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getInicioPF1()).getTime());
			formHorario.setInicioPF1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setInicioPF1Minutos(
					"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setInicioPF1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Anterior
				formHorario.setDiaAnteriorPF1(Constants.DIA_ANTERIOR);
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getFimPF1()).getTime());
			formHorario.setFimPF1Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setFimPF1Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setFimPF1Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Seguinte
				formHorario.setDiaSeguintePF1(Constants.DIA_SEGUINTE);
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getInicioPF2()).getTime());
			formHorario.setInicioPF2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setInicioPF2Minutos(
					"0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setInicioPF2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Anterior
				formHorario.setDiaAnteriorPF2(Constants.DIA_ANTERIOR);
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getFimPF2()).getTime());
			formHorario.setFimPF2Horas((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				formHorario.setFimPF2Minutos("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				formHorario.setFimPF2Minutos((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
				//contagem no Dia Seguinte
				formHorario.setDiaSeguintePF2(Constants.DIA_SEGUINTE);
			}
		} else {
			formHorario.setInicioPF1Horas("--");
			formHorario.setInicioPF1Minutos("--");

			formHorario.setFimPF1Horas("--");
			formHorario.setFimPF1Minutos("--");

			formHorario.setInicioPF2Horas("--");
			formHorario.setInicioPF2Minutos("--");

			formHorario.setFimPF2Horas("--");
			formHorario.setFimPF2Minutos("--");
		}
	} /* setFormAssociarHorarioTipoConfirmar */

	public String descricaoHorario(Locale locale, Horario horario, ArrayList listaRegimes) {
		String descricao = null;
		ResourceBundle bundle = ResourceBundle.getBundle(Constants.APPLICATION_RESOURCES, locale);
		Calendar calendar = Calendar.getInstance();

		String inicioHN1Horas = null;
		String inicioHN1Minutos = null;
		String fimHN1Horas = null;
		String fimHN1Minutos = null;

		String inicioHN2Horas = null;
		String inicioHN2Minutos = null;
		String fimHN2Horas = null;
		String fimHN2Minutos = null;

		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioHN1()).getTime());
		inicioHN1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			inicioHN1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			inicioHN1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimHN1()).getTime());
		fimHN1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			fimHN1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			fimHN1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getInicioHN2()).getTime());
		inicioHN2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			inicioHN2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			inicioHN2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horario.getFimHN2()).getTime());
		fimHN2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			fimHN2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			fimHN2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		// modalidade 
		descricao = bundle.getString(horario.getModalidade()) + ", ";

		// regimes 
		if (listaRegimes != null) {
			ListIterator iterListaRegimes = listaRegimes.listIterator();
			String regime = null;
			String auxRegime = null;
			descricao = descricao.concat(" ");

			while (iterListaRegimes.hasNext()) {
				regime = (String)iterListaRegimes.next();
				auxRegime = (String)bundle.getString(regime);
				if (auxRegime != null) {
					regime = auxRegime;
				}
				if (!iterListaRegimes.hasNext()) {
					descricao = descricao.concat(regime);
				} else {
					descricao = descricao.concat(regime + ", ");
				}
			}
		}

		// Periodos Normais 
		descricao =
			descricao.concat(
				" "
					+ inicioHN1Horas
					+ ":"
					+ inicioHN1Minutos
					+ "-"
					+ fimHN1Horas
					+ ":"
					+ fimHN1Minutos
					+ " <> "
					+ inicioHN2Horas
					+ ":"
					+ inicioHN2Minutos
					+ "-"
					+ fimHN2Horas
					+ ":"
					+ fimHN2Minutos);

		return descricao;
	} /* descricaoHorario */

	public String descricaoHorarioTipo(
		Locale locale,
		HorarioTipo horarioTipo,
		ArrayList listaRegimes) {
		String descricao = null;
		Calendar calendar = Calendar.getInstance();
		String inicioPF1Horas = null;
		String inicioPF1Minutos = null;
		String fimPF1Horas = null;
		String fimPF1Minutos = null;

		String inicioPF2Horas = null;
		String inicioPF2Minutos = null;
		String fimPF2Horas = null;
		String fimPF2Minutos = null;

		descricao = new String((new Float(horarioTipo.getDuracaoSemanal())).intValue() + " horas ");

		if (!listaRegimes.contains(Constants.IPF)) { //Regime: new String("isencaoPeriodoFixo")
			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getInicioPF1()).getTime());
			inicioPF1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				inicioPF1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				inicioPF1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getFimPF1()).getTime());
			fimPF1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				fimPF1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				fimPF1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getInicioPF2()).getTime());
			inicioPF2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				inicioPF2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				inicioPF2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}

			calendar.clear();
			calendar.setTimeInMillis((horarioTipo.getFimPF2()).getTime());
			fimPF2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
			if (calendar.get(Calendar.MINUTE) < 10) {
				fimPF2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
			} else {
				fimPF2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
			}
			descricao =
				descricao.concat(
					" ("
						+ inicioPF1Horas
						+ ":"
						+ inicioPF1Minutos
						+ " - "
						+ fimPF1Horas
						+ ":"
						+ fimPF1Minutos
						+ " <> "
						+ inicioPF2Horas
						+ ":"
						+ inicioPF2Minutos
						+ " - "
						+ fimPF2Horas
						+ ":"
						+ fimPF2Minutos
						+ ")");
		}

		ResourceBundle bundle = ResourceBundle.getBundle(Constants.APPLICATION_RESOURCES, locale);
		ListIterator iterListaRegimes = listaRegimes.listIterator();
		String regime = null;
		String auxRegime = null;
		descricao = descricao.concat(" ");

		while (iterListaRegimes.hasNext()) {
			regime = (String)iterListaRegimes.next();
			auxRegime = (String)bundle.getString(regime);
			if (auxRegime != null) {
				regime = auxRegime;
			}
			if (!iterListaRegimes.hasNext()) {
				descricao = descricao.concat(regime);
			} else {
				descricao = descricao.concat(regime + ", ");
			}
		}
		return descricao;
	} /* descricaoHorarioTipo */

	public String periodoTrabalhoHorarioTipo(HorarioTipo horarioTipo) {
		String periodoTrabalho = new String();
		Calendar calendar = Calendar.getInstance();
		String inicioHN1Horas = null;
		String inicioHN1Minutos = null;
		String fimHN1Horas = null;
		String fimHN1Minutos = null;

		String inicioHN2Horas = null;
		String inicioHN2Minutos = null;
		String fimHN2Horas = null;
		String fimHN2Minutos = null;

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioHN1()).getTime());
		inicioHN1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			inicioHN1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			inicioHN1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimHN1()).getTime());
		fimHN1Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			fimHN1Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			fimHN1Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getInicioHN2()).getTime());
		inicioHN2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			inicioHN2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			inicioHN2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}

		calendar.clear();
		calendar.setTimeInMillis((horarioTipo.getFimHN2()).getTime());
		fimHN2Horas = ((new Integer(calendar.get(Calendar.HOUR_OF_DAY))).toString());
		if (calendar.get(Calendar.MINUTE) < 10) {
			fimHN2Minutos = ("0" + (new Integer(calendar.get(Calendar.MINUTE))).toString());
		} else {
			fimHN2Minutos = ((new Integer(calendar.get(Calendar.MINUTE))).toString());
		}
		periodoTrabalho =
			inicioHN1Horas
				+ ":"
				+ inicioHN1Minutos
				+ " - "
				+ fimHN1Horas
				+ ":"
				+ fimHN1Minutos
				+ " <> "
				+ inicioHN2Horas
				+ ":"
				+ inicioHN2Minutos
				+ " - "
				+ fimHN2Horas
				+ ":"
				+ fimHN2Minutos;

		return periodoTrabalho;
	} /* periodoTrabalhoHorarioTipo */

	public void setSaldosHorarioVerbeteBody(
		Horario horario,
		ArrayList listaRegimes,
		ArrayList listaParamJustificacoes,
		ArrayList listaMarcacoesPonto,
		ArrayList listaSaldos) {
		Calendar calendarioSaida = Calendar.getInstance();
		calendarioSaida.setLenient(false);
		calendarioSaida.clear();

		Calendar calendario = Calendar.getInstance();
		calendario.setLenient(false);
		calendario.clear();

		MarcacaoPonto entrada = null;
		MarcacaoPonto saida = null;

		long saldo = ((Long)listaSaldos.get(0)).longValue();
//		System.out.println("saldo antes dos calculos da refeicao: " + saldo);
		long intervaloRefeicao = 0;

		ListIterator iterador = listaMarcacoesPonto.listIterator();
		if (iterador.hasNext()) {
			// saldo da refeicao
			// o intervalo para refeicao deve ser feito no meio de dois periodos de trabalho.
			// por isso conta o primeiro intervalo que estiver contido no intervalo para refeicao 
			// estipulado para este horario. 
			entrada = (MarcacaoPonto)iterador.next(); // primeira entrada

			// se a primeira entrada do dia est� contida no intervalo de refeicao � como se
			// a pessoa tivesse feito a refeicao antes de entrar ao servico 
			if (entrada.getData().getTime() > horario.getInicioRefeicao().getTime()
				&& entrada.getData().getTime() < horario.getFimRefeicao().getTime()) {
				intervaloRefeicao = entrada.getData().getTime() - horario.getInicioRefeicao().getTime();
				horario.setFimRefeicao(new Timestamp(entrada.getData().getTime()));
			} else if (entrada.getData().getTime() == horario.getFimRefeicao().getTime()) {
				intervaloRefeicao = entrada.getData().getTime() - horario.getInicioRefeicao().getTime();
			} else {
				if (listaMarcacoesPonto.size() >= 3) {
					while (iterador.hasNext()) {
						saida = (MarcacaoPonto)iterador.next();
						// se existe uma saida e uma entrada
						if (iterador.hasNext()) {

							entrada = (MarcacaoPonto)iterador.next();
							// testar a saida
							if (saida.getData().getTime() >= horario.getInicioRefeicao().getTime()
								&& saida.getData().getTime() < horario.getFimRefeicao().getTime()) {
								intervaloRefeicao = horario.getFimRefeicao().getTime() - saida.getData().getTime();
								horario.setInicioRefeicao(new Timestamp(saida.getData().getTime()));
							} else if (saida.getData().getTime() < horario.getInicioRefeicao().getTime()) {
								intervaloRefeicao =
									horario.getFimRefeicao().getTime() - horario.getInicioRefeicao().getTime();
							}
							// testar a entrada
							if (entrada.getData().getTime() > horario.getInicioRefeicao().getTime()
								&& entrada.getData().getTime() <= horario.getFimRefeicao().getTime()) {
								intervaloRefeicao =
									intervaloRefeicao
										+ (entrada.getData().getTime() - horario.getFimRefeicao().getTime());
								horario.setFimRefeicao(new Timestamp(entrada.getData().getTime()));
							} else if (entrada.getData().getTime() <= horario.getInicioRefeicao().getTime()) {
								intervaloRefeicao =
									intervaloRefeicao
										- (horario.getFimRefeicao().getTime() - horario.getInicioRefeicao().getTime());
							}
						} else {
							if (saida.getData().getTime() > horario.getInicioRefeicao().getTime()
								&& saida.getData().getTime() < horario.getFimRefeicao().getTime()) {
								intervaloRefeicao = horario.getFimRefeicao().getTime() - saida.getData().getTime();
							} else if (saida.getData().getTime() == horario.getInicioRefeicao().getTime()) {
								intervaloRefeicao = horario.getFimRefeicao().getTime() - saida.getData().getTime();
							}
						}
						if (intervaloRefeicao > 0) {
							// encontrou o intervalo de refeicao entao sai do ciclo
							break;
						}
					}
				} else {
					// valor que seja diferente de zero para o caso de duas marcacoes que nao estao contidas no intervalo de refeicao.
					// neste caso nao desconta a hora de refeicao
					if ((listaMarcacoesPonto.size() == 2)) {
						saida = (MarcacaoPonto)iterador.next();
						// se existe uma saida e uma entrada

						if (saida.getData().getTime() > horario.getInicioRefeicao().getTime()
							&& saida.getData().getTime() < horario.getFimRefeicao().getTime()) {
							intervaloRefeicao = horario.getFimRefeicao().getTime() - saida.getData().getTime();
							horario.setInicioRefeicao(new Timestamp(saida.getData().getTime()));
						} else if (saida.getData().getTime() == horario.getInicioRefeicao().getTime()) {
							intervaloRefeicao = horario.getFimRefeicao().getTime() - saida.getData().getTime();
						}
					}
				}
			}
//			System.out.println("intervalo de refeicao sem descontos: " + intervaloRefeicao);

			if (intervaloRefeicao >= horario.getIntervaloMinimoRefeicao().getTime()
				&& intervaloRefeicao < horario.getDescontoObrigatorioRefeicao().getTime()
				&& intervaloRefeicao != 0) {
				saldo = saldo - (horario.getDescontoObrigatorioRefeicao().getTime() - intervaloRefeicao);
			} else if (
				intervaloRefeicao < horario.getIntervaloMinimoRefeicao().getTime()
					|| intervaloRefeicao == 0) {
				if (listaParamJustificacoes.size() > 0) {
					saldo = saldo - horario.getDescontoObrigatorioRefeicao().getTime();
				} else {
					// desconta o dia todo segundo consta do regulamento de assiduidade.
					// horario normal
					Float duracaoDiaria =
						new Float(horario.getDuracaoSemanal() / Constants.SEMANA_TRABALHO_FLEXIVEL);
					saldo =
						0
							- (duracaoDiaria.intValue() * 3600 * 1000
								+ new Float((duracaoDiaria.floatValue() - duracaoDiaria.intValue()) * 3600 * 1000)
									.longValue());

					listaSaldos.set(0, new Long(saldo));

					// plataformas fixas
					//if(!listaRegimes.contains("isencaoPeriodoFixo")){ quando estiver a funcionar a nossa base de dados
					if (horario.getInicioPF1() != null) {
						long saldoInjust =
							horario.getFimPF1().getTime()
								- horario.getInicioPF1().getTime()
								+ horario.getFimPF2().getTime()
								- horario.getInicioPF2().getTime();
						listaSaldos.set(1, new Long(-saldoInjust));
					}
					return;
				}
			}
		}
		//System.out.println("saldo depois do calculo da refeicao sem tirar 7 horas: " + saldo);

		// saldo do horario normal
		Float duracaoDiaria =
			new Float(horario.getDuracaoSemanal() / Constants.SEMANA_TRABALHO_FLEXIVEL);
		saldo =
			saldo
				- (duracaoDiaria.intValue() * 3600 * 1000
					+ new Float((duracaoDiaria.floatValue() - duracaoDiaria.intValue()) * 3600 * 1000)
						.longValue());

		listaSaldos.set(0, new Long(saldo));

		// saldo das plataformas fixas
		//if(!listaRegimes.contains("isencaoPeriodoFixo")){ quando estiver a funcionar a nossa base de dados
		if (horario.getInicioPF1() != null) {
			iterador = listaMarcacoesPonto.listIterator();
			long trabalhouPF = 0;

			while (iterador.hasNext()) {
//				System.out.println("ciclo do calculo do periodo fixo");
//				System.out.println("valor de trabalhouPF inicio: " + trabalhouPF);
				entrada = (MarcacaoPonto)iterador.next();
				if (iterador.hasNext()) {
					/*System.out.println(
						"calendario de entrada: "
							+ calendario.get(Calendar.DAY_OF_MONTH)
							+ "/"
							+ calendario.get(Calendar.MONTH)
							+ "/"
							+ calendario.get(Calendar.YEAR)
							+ " "
							+ calendario.get(Calendar.HOUR_OF_DAY)
							+ ":"
							+ calendario.get(Calendar.MINUTE)
							+ ":"
							+ calendario.get(Calendar.SECOND));*/

					saida = (MarcacaoPonto)iterador.next();

					// primeiro periodo fixo
					if (entrada.getData().getTime() >= horario.getInicioPF1().getTime()
						&& entrada.getData().getTime() <= horario.getFimPF1().getTime()) {
						trabalhouPF =
							trabalhouPF + (horario.getFimPF1().getTime() - entrada.getData().getTime());
					} else if (entrada.getData().getTime() < horario.getInicioPF1().getTime()) {
						trabalhouPF =
							trabalhouPF + (horario.getFimPF1().getTime() - horario.getInicioPF1().getTime());
					}
					if (saida.getData().getTime() >= horario.getInicioPF1().getTime()
						&& saida.getData().getTime() <= horario.getFimPF1().getTime()) {
						trabalhouPF = trabalhouPF + (saida.getData().getTime() - horario.getFimPF1().getTime());
					} else if (saida.getData().getTime() < horario.getInicioPF1().getTime()) {
						trabalhouPF =
							trabalhouPF - (horario.getFimPF1().getTime() - horario.getInicioPF1().getTime());
					}

					// segundo periodo fixo
					if (entrada.getData().getTime() >= horario.getInicioPF2().getTime()
						&& entrada.getData().getTime() <= horario.getFimPF2().getTime()) {
						trabalhouPF =
							trabalhouPF + (horario.getFimPF2().getTime() - entrada.getData().getTime());
					} else if (entrada.getData().getTime() < horario.getInicioPF2().getTime()) {
						trabalhouPF =
							trabalhouPF + (horario.getFimPF2().getTime() - horario.getInicioPF2().getTime());
					}
					if (saida.getData().getTime() >= horario.getInicioPF2().getTime()
						&& saida.getData().getTime() <= horario.getFimPF2().getTime()) {
						trabalhouPF = trabalhouPF + (saida.getData().getTime() - horario.getFimPF2().getTime());
					} else if (saida.getData().getTime() < horario.getInicioPF2().getTime()) {
						trabalhouPF =
							trabalhouPF - (horario.getFimPF2().getTime() - horario.getInicioPF2().getTime());
					}
//					System.out.println("valor de trabalhouPF fim: " + trabalhouPF);
				}
			}
//			System.out.println("o funcionario trabalhou no periodo fixo: " + trabalhouPF);
			long saldoInjust =
				trabalhouPF
					- (horario.getFimPF1().getTime()
						- horario.getInicioPF1().getTime()
						+ horario.getFimPF2().getTime()
						- horario.getInicioPF2().getTime());

			listaSaldos.set(1, new Long(saldoInjust));
		}
	} /* setSaldosHorarioVerbeteBody */

	public long calcularTrabalhoNocturno(
		Horario horario,
		MarcacaoPonto entrada,
		MarcacaoPonto saida) {
		long saldoNocturno = 0;

		// calculo do trabalho nocturno normal para os horario que ultrapassem o inicio do trabalho nocturno
		if (horario.getFimHN2().getTime() >= Constants.INICIO_TRABALHO_NOCTURNO) {
			if (entrada.getData().getTime() >= Constants.INICIO_TRABALHO_NOCTURNO
				&& entrada.getData().getTime() <= horario.getFimHN2().getTime()) {
				saldoNocturno =
					saldoNocturno + (horario.getFimHN2().getTime() - entrada.getData().getTime());
			} else if (entrada.getData().getTime() < Constants.INICIO_TRABALHO_NOCTURNO) {
				saldoNocturno =
					saldoNocturno + (horario.getFimHN2().getTime() - Constants.INICIO_TRABALHO_NOCTURNO);
			}
			if (saida.getData().getTime() >= Constants.INICIO_TRABALHO_NOCTURNO
				&& saida.getData().getTime() <= horario.getFimHN2().getTime()) {
				saldoNocturno = saldoNocturno + (saida.getData().getTime() - horario.getFimHN2().getTime());
			} else if (saida.getData().getTime() < Constants.INICIO_TRABALHO_NOCTURNO) {
				saldoNocturno =
					saldoNocturno - (horario.getFimHN2().getTime() - Constants.INICIO_TRABALHO_NOCTURNO);
			}
		}
		return saldoNocturno;
	} /* calcularTrabalhoNocturno */

	public void calcularHorasExtraordinarias(
		Horario horario,
		ArrayList listaMarcacoesPonto,
		ArrayList listaSaldos) {
		if (listaMarcacoesPonto.size() > 0) {
			ListIterator iteradorMarcacoes = listaMarcacoesPonto.listIterator();
			MarcacaoPonto entrada = null;
			MarcacaoPonto saida = null;
			long fimHorario = 0;
			long saldoExtraordinario = 0;

			long saldoPrimEscalao = 0;
			long saldoSegEscalao = 0;
			long saldoDepoisSegEscalao = 0;

			while (iteradorMarcacoes.hasNext()) {
				entrada = (MarcacaoPonto)iteradorMarcacoes.next();
			
				if (iteradorMarcacoes.hasNext()) {
					saida = (MarcacaoPonto)iteradorMarcacoes.next();

					if (horario.getFimHN2().getTime() > Constants.INICIO_TRABALHO_NOCTURNO) {
						fimHorario = horario.getFimHN2().getTime();
					} else {
						fimHorario = Constants.INICIO_TRABALHO_NOCTURNO;
					}

					// calculo do trabalho extraordin�rio nocturno
					if (entrada.getData().getTime() >= fimHorario
						&& entrada.getData().getTime() <= Constants.FIM_TRABALHO_NOCTURNO) {
						saldoExtraordinario =
							saldoExtraordinario + (Constants.FIM_TRABALHO_NOCTURNO - entrada.getData().getTime());
					} else if (entrada.getData().getTime() < fimHorario) {
						saldoExtraordinario =
							saldoExtraordinario + (Constants.FIM_TRABALHO_NOCTURNO - fimHorario);
					}
					if (saida.getData().getTime() >= fimHorario
						&& saida.getData().getTime() <= Constants.FIM_TRABALHO_NOCTURNO) {
						saldoExtraordinario =
							saldoExtraordinario + (saida.getData().getTime() - Constants.FIM_TRABALHO_NOCTURNO);
					} else if (saida.getData().getTime() < fimHorario) {
						saldoExtraordinario =
							saldoExtraordinario - (Constants.FIM_TRABALHO_NOCTURNO - fimHorario);
					}
				}
			}
			
			if(saldoExtraordinario > ((Long) listaSaldos.get(0)).longValue()){
				saldoExtraordinario = ((Long) listaSaldos.get(0)).longValue();
			}
 
			//calculo dos escal�es do trabalho extraordin�rio nocturno
			if (saldoExtraordinario <= Constants.PRIMEIRO_ESCALAO) {
				saldoPrimEscalao = saldoPrimEscalao + saldoExtraordinario;
			} else {
				saldoPrimEscalao = saldoPrimEscalao + Constants.PRIMEIRO_ESCALAO;
				if (saldoExtraordinario <= Constants.SEGUNDO_ESCALAO) {
					saldoSegEscalao = saldoSegEscalao + (saldoExtraordinario - Constants.PRIMEIRO_ESCALAO);
				} else {
					saldoSegEscalao =
						saldoSegEscalao + (Constants.SEGUNDO_ESCALAO - Constants.PRIMEIRO_ESCALAO);
					saldoDepoisSegEscalao =
						saldoDepoisSegEscalao + (saldoExtraordinario - Constants.SEGUNDO_ESCALAO);
				}
			}

			listaSaldos.set(8, new Long(saldoPrimEscalao));
			listaSaldos.set(9, new Long(saldoSegEscalao));
			listaSaldos.set(10, new Long(saldoDepoisSegEscalao));

			//calculo do trabalho extraordin�rio diurno
			saldoExtraordinario = ((Long)listaSaldos.get(0)).longValue() - saldoExtraordinario;

			saldoPrimEscalao = 0;
			saldoSegEscalao = 0;
			saldoDepoisSegEscalao = 0;
			//calculo dos escal�es do trabalho extraordin�rio diurno
			if (saldoExtraordinario <= Constants.PRIMEIRO_ESCALAO) {
				saldoPrimEscalao = saldoPrimEscalao + saldoExtraordinario;
			} else {
				saldoPrimEscalao = saldoPrimEscalao + Constants.PRIMEIRO_ESCALAO;
				if (saldoExtraordinario <= Constants.SEGUNDO_ESCALAO) {
					saldoSegEscalao = saldoSegEscalao + (saldoExtraordinario - Constants.PRIMEIRO_ESCALAO);
				} else {
					saldoSegEscalao =
						saldoSegEscalao + (Constants.SEGUNDO_ESCALAO - Constants.PRIMEIRO_ESCALAO);
					saldoDepoisSegEscalao =
						saldoDepoisSegEscalao + (saldoExtraordinario - Constants.SEGUNDO_ESCALAO);
				}
			}

			listaSaldos.set(2, new Long(saldoPrimEscalao));
			listaSaldos.set(3, new Long(saldoSegEscalao));
			listaSaldos.set(4, new Long(saldoDepoisSegEscalao));
		}
	} /* calcularHorasExtraorinarias */

	public long duracaoPF(Horario horario, ArrayList listaRegimes) {
		if (!listaRegimes.contains(Constants.IPF)) {
			return Constants.PLATAFORMAS_FIXAS_FLEXIVEL;
		}
		return 0;
	} /* duracaoPF */

	public long duracaoDiaria(Horario horario) {
		return (
			(new Float(Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
				.intValue()
				* 3600
				* 1000)
				+ new Float(
					(new Float(Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
						.floatValue()
						- new Float(Constants.DURACAO_SEMANAL_FLEXIVEL / Constants.SEMANA_TRABALHO_FLEXIVEL)
							.intValue())
						* 3600
						* 1000)
					.longValue());
	} /* duracaoDiaria */

	public int mapeamentoFechoMes(Horario horario, ArrayList listaRegimes) {
		if (listaRegimes.contains(Constants.TE)) {
			return Constants.MAP_TE;
		}	else if (listaRegimes.contains(Constants.APOIOFAMILIA)) {
			return Constants.MAP_AF;
		} else {
			return Constants.MAP_FLEXIVEL; 
		}		
	} /* mapeamentoFechoMes */
}