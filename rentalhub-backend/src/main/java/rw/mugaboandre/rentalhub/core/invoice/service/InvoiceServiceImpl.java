package rw.mugaboandre.rentalhub.core.invoice.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.mugaboandre.rentalhub.core.client.repository.IClientRepository;
import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceDto;
import rw.mugaboandre.rentalhub.core.invoice.dto.InvoiceResponseDto;
import rw.mugaboandre.rentalhub.core.invoice.model.Invoice;
import rw.mugaboandre.rentalhub.core.invoice.repository.InvoiceRepository;
import rw.mugaboandre.rentalhub.core.property.repository.IPropertyRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final IClientRepository clientRepository;
    private final IPropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    @Override
    public InvoiceResponseDto createInvoice(InvoiceDto dto) {
        Invoice invoice = modelMapper.map(dto, Invoice.class);

        invoice.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found")));
        invoice.setProperty(propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found")));

        return modelMapper.map(invoiceRepository.save(invoice), InvoiceResponseDto.class);
    }

    @Override
    public InvoiceResponseDto getInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return modelMapper.map(invoice, InvoiceResponseDto.class);
    }

    @Override
    public List<InvoiceResponseDto> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponseDto updateInvoice(UUID id, InvoiceDto dto) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        modelMapper.map(dto, invoice);

        invoice.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found")));
        invoice.setProperty(propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found")));

        return modelMapper.map(invoiceRepository.save(invoice), InvoiceResponseDto.class);
    }

    @Override
    public void deleteInvoice(UUID id) {
        invoiceRepository.deleteById(id);
    }

    // In InvoiceServiceImpl
    @Override
    public Invoice getEntity(UUID id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID " + id));
    }

}
