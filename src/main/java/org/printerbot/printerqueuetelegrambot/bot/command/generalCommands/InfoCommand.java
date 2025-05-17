package org.printerbot.printerqueuetelegrambot.bot.command.generalCommands;

import lombok.RequiredArgsConstructor;
import org.printerbot.printerqueuetelegrambot.bot.config.WhiteList;
import org.printerbot.printerqueuetelegrambot.bot.constants.ConstantMessages;
import org.printerbot.printerqueuetelegrambot.model.dto.PlasticDto;
import org.printerbot.printerqueuetelegrambot.model.dto.PrinterDto;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PlasticDaoService;
import org.printerbot.printerqueuetelegrambot.model.service.daoService.PrinterDaoService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InfoCommand implements GeneralCommand {

	private final PrinterDaoService printerDaoService;

	private final PlasticDaoService plasticDaoService;

	private final WhiteList whiteList;

	@Override
	public SendMessage apply(Update update) {
		List<PrinterDto> allAvailablePrinters = printerDaoService.getAllAvailablePrinters();
		List<PlasticDto> allAvailablePlastic = plasticDaoService.getAllAvailablePlastic();

		StringBuilder builder = new StringBuilder()
				.append("Available Printers:\n");
		for(var pr : allAvailablePrinters) {
			builder.append(" • ")
					.append(ConstantMessages.INFO.getMessage(
					pr.getPrinterInfo(),
					pr.getFeatures()
			)).append("\n");
		}

		builder.append("\nAvailable Plastic:\n");
		for (var pl : allAvailablePlastic) {
			builder.append(" • ")
					.append(ConstantMessages.INFO.getMessage(
					pl.getPlasticInfo(),
					pl.getDescription()
			)).append("\n");
		}

		builder.append("\n")
				.append(ConstantMessages.AUTHOR_INFO.getMessage())
				.append("\n\n")
				.append(ConstantMessages.ADMINS_INFO.getMessage());

		for (var admin : whiteList.getAdmins()) {
			builder.append(" • ")
					.append("@")
					.append(admin)
					.append("\n");
		}

		return createSendMessage(update, builder.toString());
	}

}
