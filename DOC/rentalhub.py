# Install graphviz and necessary dependencies
!pip install graphviz
!apt-get install graphviz -y

# Import the Graphviz library
from graphviz import Digraph
from IPython.display import Image

# Create the UML diagram
uml = Digraph('RealEstateUML', filename='real_estate_uml_compact', format='png')
uml.attr(rankdir='LR', size='12')  # Left to right layout

# --------------------
# Abstract Entities
# --------------------
with uml.subgraph(name='cluster_abstract') as c:
    c.attr(label='Abstract Entities', style='filled', color='lightgrey')
    c.node('AbstractBaseEntity', 'AbstractBaseEntity\n+id\n+is_active', shape='box', style='filled', fillcolor='white')
    c.node('AbstractAuditEntity', 'AbstractAuditEntity\n+created_at\n+updated_at\n+created_by\n+updated_by', shape='box', style='filled', fillcolor='white')
    c.edge('AbstractAuditEntity', 'AbstractBaseEntity', arrowhead='onormal', color='blue', label='inherits')

# --------------------
# Person Hierarchy
# --------------------
with uml.subgraph(name='cluster_person') as c:
    c.attr(label='Person Entities', style='filled', color='lightblue')
    c.node('Person', 'Person\n+first_name\n+last_name\n+email\n+phone\n+contact_pref:ContactPref', shape='box', style='filled', fillcolor='white')
    c.node('Owner', 'Owner\n+properties_owned', shape='box', style='filled', fillcolor='white')
    c.node('Client', 'Client\n+preferences\n+lease_history\n+transaction_log', shape='box', style='filled', fillcolor='white')
    c.node('Admin', 'Admin\n+role:Role\n+permissions', shape='box', style='filled', fillcolor='white')
    c.edge('AbstractBaseEntity', 'Person', arrowhead='onormal', color='blue')
    c.edge('Person', 'Owner', arrowhead='onormal', color='blue')
    c.edge('Person', 'Client', arrowhead='onormal', color='blue')
    c.edge('Person', 'Admin', arrowhead='onormal', color='blue')

# --------------------
# Property & Lease
# --------------------
with uml.subgraph(name='cluster_property') as c:
    c.attr(label='Property & Lease', style='filled', color='lightgreen')
    c.node('Property', 'Property\n+type\n+address\n+price\n+status:PropertyStatus\n+media_urls', shape='box', style='filled', fillcolor='white')
    c.node('LeaseContract', 'LeaseContract\n+start_date\n+end_date\n+terms\n+status:LeaseStatus', shape='box', style='filled', fillcolor='white')
    c.edge('AbstractBaseEntity', 'Property', arrowhead='onormal', color='blue')
    c.edge('AbstractBaseEntity', 'LeaseContract', arrowhead='onormal', color='blue')
    c.edge('Owner', 'Property', label='1..* owns', arrowhead='vee', color='black')
    c.edge('Client', 'Property', label='0..1 rents', arrowhead='vee', color='black')
    c.edge('Property', 'LeaseContract', label='1..* has', arrowhead='vee', color='black')
    c.edge('Client', 'LeaseContract', label='0..* signed', arrowhead='vee', color='black')

# --------------------
# Financial Entities
# --------------------
with uml.subgraph(name='cluster_financial') as c:
    c.attr(label='Financial', style='filled', color='orange')
    c.node('Invoice', 'Invoice\n+amount\n+due_date\n+status:InvoiceStatus', shape='box', style='filled', fillcolor='white')
    c.node('Payment', 'Payment\n+amount\n+date\n+method:PaymentMethod\n+reference_no', shape='box', style='filled', fillcolor='white')
    c.edge('AbstractBaseEntity', 'Invoice', arrowhead='onormal', color='blue')
    c.edge('AbstractBaseEntity', 'Payment', arrowhead='onormal', color='blue')
    c.edge('Client', 'Invoice', label='0..* billed', arrowhead='vee', color='black')
    c.edge('Property', 'Invoice', label='0..* for', arrowhead='vee', color='black')
    c.edge('Invoice', 'Payment', label='0..* pays', arrowhead='vee', color='black')

# --------------------
# Maintenance Entities
# --------------------
with uml.subgraph(name='cluster_maintenance') as c:
    c.attr(label='Maintenance', style='filled', color='pink')
    c.node('RepairRequest', 'RepairRequest\n+description\n+status:RepairStatus\n+assigned_team\n+est_cost\n+act_cost', shape='box', style='filled', fillcolor='white')
    c.edge('AbstractBaseEntity', 'RepairRequest', arrowhead='onormal', color='blue')
    c.edge('Property', 'RepairRequest', label='0..* requested_for', arrowhead='vee', color='black')

# --------------------
# Enumerations
# --------------------
with uml.subgraph(name='cluster_enums') as c:
    c.attr(label='Enumerations', style='filled', color='lightyellow')
    enums = {
        'ContactPref': ['EMAIL', 'PHONE', 'WHATSAPP'],
        'Role': ['ADMIN', 'MANAGER', 'SUPERVISOR'],
        'PropertyStatus': ['AVAILABLE', 'RENTED', 'MAINTENANCE'],
        'LeaseStatus': ['ACTIVE', 'EXPIRED', 'TERMINATED'],
        'InvoiceStatus': ['PENDING', 'PAID', 'OVERDUE'],
        'PaymentMethod': ['CASH', 'CARD', 'TRANSFER', 'MOBILE'],
        'RepairStatus': ['PENDING', 'IN_PROGRESS', 'COMPLETED']
    }
    for enum, values in enums.items():
        c.node(enum, f"{enum}\n" + "\n".join(values), shape='ellipse', style='filled', fillcolor='white')

# Enum associations
uml.edge('Person', 'ContactPref', style='dashed', color='black')
uml.edge('Admin', 'Role', style='dashed', color='black')
uml.edge('Property', 'PropertyStatus', style='dashed', color='black')
uml.edge('LeaseContract', 'LeaseStatus', style='dashed', color='black')
uml.edge('Invoice', 'InvoiceStatus', style='dashed', color='black')
uml.edge('Payment', 'PaymentMethod', style='dashed', color='black')
uml.edge('RepairRequest', 'RepairStatus', style='dashed', color='black')

# --------------------
# Render and display the diagram
# --------------------
uml.render('/content/real_estate_uml_compact', view=False)  # Save to Colab's filesystem
Image('/content/real_estate_uml_compact.png')  # Display the image